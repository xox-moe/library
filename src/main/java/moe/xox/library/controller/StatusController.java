package moe.xox.library.controller;

import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookStatusRepository;
import moe.xox.library.dao.entity.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("status")
public class StatusController extends BaseController {


    @Autowired
    BookStatusRepository bookStatusRepository;

    @RequestMapping("listAllBookStatus")
    public ReturnBean listAllBookStatus(){
        List<BookStatus> list = bookStatusRepository.findAll();
        return getSuccess("OK",list,list.size());
    }

}
