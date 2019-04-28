package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.BookMessage;
import moe.xox.library.dao.entity.BorrowInfo;
import moe.xox.library.dao.entity.User;
import moe.xox.library.project.BookQualityEnum;
import moe.xox.library.project.BookStatusEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("churukuguanli")
public class BookController extends BaseController {
    @Autowired
    BookRepository bookRepository;

    /**
     *
     */
    @RequestMapping(value = "listNewBook", method = RequestMethod.GET)
    public ReturnBean listNewBook() {
        List<JSONObject> jsonObjects = bookRepository.listNewBook();
        return getSuccess("success", jsonObjects, jsonObjects.size());
    }

    /**
     * 分页!
     * 返回Book表中的图书信息
     * method = RequestMethod.GET
     *
     * @return json
     */
    @RequestMapping(value = "showBookManagerTable", method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listAllBook(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Book> bookPage = bookRepository.findAllByStatusIsTrue(pageable);
        List<Book> bookList = bookPage.getContent();
        return getSuccess("success", bookList, bookPage.getTotalElements());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listAllBookInfo(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> bookPage = bookRepository.listAllBookInfo(pageable);
        List<JSONObject> bookList = bookPage.getContent();
        for (JSONObject object : bookList) {
            Integer qualityId = (Integer) object.get("qualityId");
            if (qualityId == null) {
                object.put("qualityName", "无数据");
            } else {
                object.put("qualityName", BookQualityEnum.getNameById(qualityId.longValue()));
            }
        }
        return getSuccess("success", bookList, bookPage.getTotalElements());
    }


    /**
     * 向Book表中新增一条图书信息
     * 反馈一条消息
     *
     * @return msg
     * <p>
     * <p>
     * private Long bookMessageId;
     * private Long bookMessageId;
     * private Long bookStatusId;
     * private Long quality;
     */
    @RequestMapping(path = "addBook", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean addBook(Long bookMessageId, Long bookStatusId, Long qualityId) {
        Book book = new Book(null, bookMessageId, bookStatusId, qualityId, null, LocalDateTime.now(), true);
//        book.setBookMessageId(null);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        book.setCreatorId(user.getUserId() + "");
//        book.setStatus(true);
//        book.setBookStatusId(BookStatusEnum.NORMAL.id);
        bookRepository.save(book);
        return getSuccess();
    }

    /**
     * 从Book表中删除几条图书信息
     *
     * @return
     */
    @RequestMapping(path = "deleteBook", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean deleteBook(@RequestBody JSONObject object) {
        List<Integer> list;

        if (object == null || !object.containsKey("list"))
            return getFailure("请选择正确的信息");
        try {
            list = (List<Integer>) object.get("list");
        } catch (Exception ex) {
            return getFailure("请求格式不正确");
        }

        List<Book> bookList = new ArrayList<>();
        for (Integer aLong : list) {
            Book book = bookRepository.findByBookId(aLong.longValue());
            book.setStatus(false);
            bookList.add(book);
        }
        bookRepository.saveAll(bookList);
        return getSuccess();
    }

    /**
     * 修改Book表中一条数据
     *
     * @param
     * @return private Long bookMessageId;
     * private Long bookMessageId;
     * private Long bookStatusId;
     * private Long quality;
     * private String creatorId;
     * private LocalDateTime createTime;
     * private Boolean status;
     */
    @RequestMapping(path = "updateBook", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean deleteBook(Long bookId, Long bookStatusId, Long qualityId) {
//        book.setStatus(true);
        Book book = bookRepository.findByBookId(bookId);
        book.setBookStatusId(bookStatusId);
        book.setQuality(qualityId);
        bookRepository.save(book);
        return getSuccess();
    }


    @RequestMapping("listBookHistory")
    public ReturnBean listBookHistory(Long bookId) {

        List<BorrowInfo> borrowInfos = bookRepository.listBookHistory(bookId);


        List<String> result = new ArrayList<>();


        for (BorrowInfo borrowInfo : borrowInfos) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("于").append(borrowInfo.getOutTime()).append("被").append(borrowInfo.getUserId()).append("借出");
            if(borrowInfo.getIfReturn()){
                stringBuilder.append("于").append(borrowInfo.getBackTime()).append("归还");
            }else {
                stringBuilder.append("未归还");
            }
            result.add(stringBuilder.toString());

        }

        return getSuccess("OK", result, result.size());

    }

}
