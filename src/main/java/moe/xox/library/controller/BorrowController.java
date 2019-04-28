package moe.xox.library.controller;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BorrowInfoRepository;
import moe.xox.library.dao.entity.BorrowInfo;
import moe.xox.library.service.BorrowService;
import moe.xox.library.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("borrow")
public class BorrowController extends BaseController {

    @Autowired
    BorrowService borrowService;

    @Autowired
    BorrowInfoRepository borrowInfoRepository;

    /**
     *
     * @param userId  用户ID
     * @param bookId 借出书的类别ID
     * @param code 预约代码
     * @return
     */
    @RequestMapping("borrowBook")
    public ReturnBean borrowBook(Long userId, Long bookId,String code) {
        int flag = borrowService.borrowBook(userId, bookId,code);
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
        return getSuccess("OK", myBorrow.getContent(), myBorrow.getTotalElements());
    }




}
