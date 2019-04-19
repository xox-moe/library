package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.BookMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    @RequestMapping(value = "showBookManagerTable",method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listAllBook(Integer page,Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Book> bookPage = bookRepository.findAllByStatusIsTrue(pageable);
        List<Book> bookList = bookPage.getContent();
        return getSuccess("success", bookList, bookPage.getTotalElements());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listAllBookInfo(Integer page,Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> bookPage = bookRepository.listAllBookInfo(pageable);
        List<JSONObject> bookList = bookPage.getContent();
        return getSuccess("success", bookList, bookPage.getTotalElements());
    }



    /**
     * 向Book表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addBook",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBook(Book book){
        book.setCreateTime(new Timestamp(System.currentTimeMillis()));
        bookRepository.save(book);
        return getSuccess();
    }

    /**
     * 从Book表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteBook",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(@RequestBody JSONObject object){

        if(object == null || !object.containsKey("list"))
            return getFailure("请选择正确的信息");
        List<Long> list = (List<Long>) object.get("list");
        List<Book> bookList = new ArrayList<>();
        for (Long aLong : list) {
            Book book = bookRepository.findByBookId(aLong);
            book.setStatus(false);
            bookList.add(book);
        }
        bookRepository.saveAll(bookList);
        return getSuccess();
    }

    /**
     * 修改Book表中一条数据
     * @param book
     * @return
     */
    @RequestMapping(path = "updateBook",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(Book book){
        bookRepository.save(book);
        return getSuccess();
    }
}
