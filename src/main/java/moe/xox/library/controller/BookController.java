package moe.xox.library.controller;


import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.BookMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("churukuguanli")
public class BookController extends BaseController {
    @Autowired
    BookRepository bookRepository;

    /**
     * 分页!
     * 返回Book表中的图书信息
     * method = RequestMethod.GET
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookManagerTable(){
        return null;
    }

    /**
     * 向Book表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBook(Book book){
        return null;
    }

    /**
     * 从Book表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(List<Integer>list){
        return null;
    }

    /**
     * 修改Book表中一条数据
     * @param book
     * @return
     */
    @RequestMapping(path = "updateBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(Book book){
        return null;
    }
}
