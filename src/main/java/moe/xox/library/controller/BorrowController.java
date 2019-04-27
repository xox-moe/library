package moe.xox.library.controller;

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
                return getFailure("该书已被这位同学借走 不要重复提交");
            case -2:
                return getFailure("书已经借出");
            case -3:
                return getFailure("请求参数不正确,不存在该书");
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

    @RequestMapping("findBorrowInfoByUserId")
    public Page<BorrowInfo> findBorrowInfoByUserId(int page, int limit){
        Long userId = ShiroUtils.getUserId();
        Pageable pageable = PageRequest.of(page - 1, limit);
        return borrowInfoRepository.findBorrowInfoByUserId(userId,pageable);
    }


}
