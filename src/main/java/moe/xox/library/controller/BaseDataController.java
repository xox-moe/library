package moe.xox.library.controller;

import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookKindRepository;
import moe.xox.library.dao.BookStatusRepository;
import moe.xox.library.dao.entity.BookKind;
import moe.xox.library.dao.entity.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("data")
public class BaseDataController extends BaseController {


    @Autowired
    BookStatusRepository bookStatusRepository;

    @Autowired
    BookKindRepository bookKindRepository;

    @RequestMapping("listAllBookStatus")
    @ResponseBody
    public ReturnBean listAllBookStatus(){
        List<BookStatus> list = bookStatusRepository.findAllOrderByBookStatusIdDesc();
        return getSuccess("OK",list,list.size());
    }

    @RequestMapping("listAllBookKind")
    @ResponseBody
    public ReturnBean listAllBookKind(){
        List<BookKind> list = bookKindRepository.findAllByStatusIsTrue();
        return getSuccess("OK",list,list.size());
    }





}
