package moe.xox.library.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookManagerTable(int page,int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<Book> bookList = bookPage.getContent();
        return getSuccess("success", bookList, bookPage.getTotalElements());
    }

    /**
     * 向Book表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBook(Book book){
        bookRepository.save(book);
        return getSuccess();
    }

    /**
     * 从Book表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(List<Integer>list){
        List<Book> bookList = new ArrayList<>();
        for (Integer integer : list) {
            Book book = new Book();
            book.setBookId(integer);
            bookList.add(book);
        }
        bookRepository.deleteAll(bookList);
        return getSuccess();
    }

    /**
     * 修改Book表中一条数据
     * @param book
     * @return
     */
    @RequestMapping(path = "updateBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(Book book){
        bookRepository.save(book);
        return getSuccess();
    }
}
