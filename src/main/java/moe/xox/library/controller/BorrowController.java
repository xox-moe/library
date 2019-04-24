package moe.xox.library.controller;

import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("borrow")
public class BorrowController extends BaseController {

    @Autowired
    BorrowService borrowService;

    @RequestMapping("borrowBook")
    public ReturnBean borrowBook(Long userId, Long bookId) {
        int flag = borrowService.borrowBook(userId, bookId);
        switch (flag) {
            case -1:
                return getFailure("该书已被这位同学借走 不要重复提交");
            case -2:
                return getFailure("书已经借出");
            case 0:
                return getSuccess();
            default:
                return getFailure("失败 原因未知，请联系管理员");
        }
    }


//    @RequestMapping("returnBook")
//    public ReturnBean returnBook(Long userId, Long bookId,Long quality) {
//        int flag = borrowService.returnBook(userId, bookId,quality);
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


}
