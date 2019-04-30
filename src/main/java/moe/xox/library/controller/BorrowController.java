package moe.xox.library.controller;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BorrowInfoRepository;
import moe.xox.library.dao.entity.BorrowInfo;
import moe.xox.library.service.BorrowService;
import moe.xox.library.utils.ShiroUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@RestController
@RequestMapping("borrow")
public class BorrowController extends BaseController {
    Logger logger = LoggerFactory.getLogger(BorrowController.class);

    @Autowired
    BorrowService borrowService;

    @Autowired
    BorrowInfoRepository borrowInfoRepository;

    /**
     *
     * @param  email 用户的邮箱
     * @param bookId 借出书的类别ID
     * @param code 预约代码
     * @return
     */
    @RequestMapping("borrowBook")
    public ReturnBean borrowBook(String email, Long bookId,String code) {
        int flag = borrowService.borrowBook(email, bookId,code);
        switch (flag) {
            case -1:
                return getFailure("预约与取出的书的类别不符");
            case -2:
                return getFailure("该预约已经取走书本了");
            case -3:
                return getFailure("数据显示本书已经借出,请管理员进行后台检查");
            case 0:
                return getSuccess();
            default:
                return getFailure("失败 原因未知，请联系管理员");
        }
    }


//    @RequestMapping("returnBook")
//    public ReturnBean returnBook(Long userId, Long bookMessageId,Long quality) {
//        int flag = borrowService.returnBook(userId, bookMessageId,quality);
//        if (flag == -1) {
//            return getFailure("查无此借书记录");
//        }
//        return getFailure("失败 原因未知，请联系管理员");
//    }


    @RequestMapping("backBook")
    public ReturnBean backBook(Long bookId, Long quality) {
        int flag = borrowService.backBook(bookId, quality);

        switch (flag) {
            case -1:
                return getFailure("查无此书");
            case -2:
                return getFailure("查无此借书记录");
            default:
                return getSuccess();
        }
    }

    /**
     * @return 查询我当前的借阅的东西的记录
     */
    @RequestMapping("listMyBorrow")
    public ReturnBean listMyBorrow(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> myBorrow = borrowInfoRepository.findBorrowInfoByUserId(ShiroUtils.getUserId(), pageable);

        for (JSONObject object : myBorrow.getContent()) {
            boolean ifReturn = (boolean) object.get("ifReturn");
            if (!ifReturn){
                Timestamp timestamp = (Timestamp) object.get("outTime");
                logger.info(String.valueOf(timestamp));
                Instant instant = Instant.ofEpochMilli(timestamp.getTime());
                ZoneId zone = ZoneId.systemDefault();
                LocalDateTime outTime =  LocalDateTime.ofInstant(instant, zone);
                LocalDateTime needReturnTime = outTime.plusMonths(6);
                if( (boolean) object.get("ifXu"))
                    needReturnTime = needReturnTime.plusMonths(3);
//                LocalDateTime needReturnTime = outTime.plusMonths(6);
                object.put("needReturnTime", needReturnTime);
                object.put("ifOutOfTime", false);
                if (needReturnTime.isBefore(LocalDateTime.now()))
                    object.put("ifOutOfTime", true);
            }
        }

        return getSuccess("OK", myBorrow.getContent(), myBorrow.getTotalElements());
    }

    @RequestMapping("xuBorrow")
    public ReturnBean xuBorrow(long borrowId){
        Long userId = ShiroUtils.getUserId();
        Optional<BorrowInfo> borrowInfo = borrowInfoRepository.findById(borrowId);
        if(borrowInfo.isPresent()){
            if(!borrowInfo.get().getUserId().equals(userId)){
                return getFailure("请对自己的借书记录进行续期");
            }
            if(borrowInfo.get().getIfXu())
                return getFailure("您已经续过了，请不要再续了");
            borrowInfo.get().setIfXu(true);
            borrowInfoRepository.save(borrowInfo.get());
            return getSuccess("续借成功");
        }
        return getFailure("查无此借书记录");
    }




}
