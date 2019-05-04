package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.AddBooksVo;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.BookMessage;
import moe.xox.library.dao.entity.BorrowInfo;
import moe.xox.library.dao.entity.User;
import moe.xox.library.project.BookQualityEnum;
import moe.xox.library.project.BookStatusEnum;
import moe.xox.library.project.FILE_PATH;
import moe.xox.library.utils.ImageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("churukuguanli")
public class BookController extends BaseController {
    Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMsgRepository bookMsgRepository;

    /**
     *
     */
    @RequestMapping(value = "listNewBook", method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listNewBook() {
        List<JSONObject> jsonObjects = bookRepository.listNewBook();
        for (JSONObject object : jsonObjects) {
            try {
                object.put("img", ImageUtil.imageToString(FILE_PATH.IMG_PATH + "\\" + object.get("imgName")));
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return getSuccess("success", jsonObjects, jsonObjects.size());
    }

    /**
     * 分页!
     * 返回Book表中的图书信息
     * <p>
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
    public ReturnBean listAllBookInfo(Integer page, Integer limit,
                                      @RequestParam(name = "bookId", required = false, defaultValue = "") String bookId,
                                      @RequestParam(name = "bookMessageName", required = false, defaultValue = "") String bookMessageName,
                                      @RequestParam(name = "author", required = false, defaultValue = "") String author,
                                      @RequestParam(name = "qualityId", required = false, defaultValue = "") String qualityId,
                                      @RequestParam(name = "publisher", required = false, defaultValue = "") String publisher,
                                      @RequestParam(name = "bookStatusId", required = false, defaultValue = "") String bookStatusId) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> bookPage = bookRepository.listAllBookInfo(pageable, bookId, bookMessageName, author, qualityId, publisher, bookStatusId);
        List<JSONObject> bookList = bookPage.getContent();
        for (JSONObject object : bookList) {
            Integer qualId = (Integer) object.get("qualityId");
            if (qualId == null) {
                object.put("qualityName", "无数据");
            } else {
                object.put("qualityName", BookQualityEnum.getNameById(qualId.longValue()));
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
//    @RequestMapping(path = "addBook", method = RequestMethod.POST)
//    @ResponseBody
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

    @RequestMapping(path = "addBook", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean addBooks(Long bookMessageId, Long bookStatusId, Long qualityId, int count) {
        for (int i = 0; i < count; i++) {
            addBook(bookMessageId, bookStatusId, qualityId);
        }
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
    @ResponseBody
    public ReturnBean listBookHistory(Long bookId) {

        List<JSONObject> borrowInfos = bookRepository.listBookHistory(bookId);


//        List<String> result = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();

        for (JSONObject borrowInfo : borrowInfos) {
            stringBuilder.append("于").append(borrowInfo.get("outTime")).append("被 id:").append(borrowInfo.get("userId"))
                    .append("姓名:").append(borrowInfo.get("realName")).append("借出");
            if ((boolean) borrowInfo.get("ifReturn")) {
                stringBuilder.append(",于").append(borrowInfo.get("backTime")).append("归还");
            } else {
                stringBuilder.append(",未归还");
            }
//            result.add(stringBuilder.toString());

            stringBuilder.append("\n");
        }

        return getSuccess("OK", stringBuilder.toString(), 1);

    }


    @RequestMapping("getBookInfoById")
    public ReturnBean getBookInfoById(Long bookId){
        Book book = bookRepository.findByBookId(bookId);
        if(book == null)
            return getFailure("数据库查无此书");
        BookMessage bookMessage = bookMsgRepository.findBookMessageByBookMessageId(book.getBookMessageId());
        JSONObject object = new JSONObject();
        object.put("name", bookMessage.getName());
        object.put("author", bookMessage.getAuthor());
        object.put("bookMessageId", bookMessage.getBookMessageId());
        object.put("publisher", bookMessage.getPublisher());
        object.put("qualityId", book.getQuality());
        object.put("bookStatusId", book.getBookStatusId());
        object.put("bookStatusName", BookStatusEnum.getNameById(book.getBookStatusId().intValue()));
        return getSuccess("OK", object,1);

    }

}
