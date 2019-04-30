package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.CollectionRepository;
import moe.xox.library.dao.HistoryRepository;
import moe.xox.library.dao.OrderRepository;
import moe.xox.library.dao.entity.BookMessage;
//import moe.xox.library.utils.ImageUtil;
import moe.xox.library.dao.entity.Collection;
import moe.xox.library.dao.entity.History;
import moe.xox.library.dao.entity.Order;
import moe.xox.library.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("tushuxinxiguanli")
public class BookMassageController extends BaseController {
    @Autowired
    BookMsgRepository bookMsgRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    OrderRepository orderRepository;


    /**
     * 分页!
     * 返回BookMsg表中的图书信息
     * method = RequestMethod.GET
     *
     * @return json
     */
    @RequestMapping(value = "showBookMsgManagerTable", method = RequestMethod.GET)
    @ResponseBody

    public ReturnBean showBookMsgManagerTable(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<BookMessage> bookMessagePage = bookMsgRepository.findAll(pageable);
        List<BookMessage> bookMessageList = bookMessagePage.getContent();
        return getSuccess("success", bookMessageList, bookMessagePage.getTotalElements());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listBookMsgManageInfo(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> bookMessagePage = bookMsgRepository.listBookMsgManageInfo(pageable);
        List<JSONObject> bookMessageList = bookMessagePage.getContent();
        return getSuccess("success", bookMessageList, bookMessagePage.getTotalElements());
    }


    @RequestMapping(value = "getBookMessageById", method = RequestMethod.GET)
    public ReturnBean getBookMessageById(Integer bookMessageId) {
        JSONObject object = bookMsgRepository.getBookMessageByBookMessageId(bookMessageId.longValue());
        Long userId = ShiroUtils.getUserId();
        History history = new History(null,userId, bookMessageId.longValue(), LocalDateTime.now());


        /**
         * 查询当前用户是否有收藏这本书 如果有收藏 将收藏的ID返回给前端
         */
        Collection collection = collectionRepository.findCollectionByUserIdAndBookMessageId(userId, bookMessageId.longValue());
        if (collection == null) {
            object.put("ifCollection", false);
            object.put("collectionId","");

        }else {
            object.put("ifCollection", true);
            object.put("collectionId", collection.getCollectionId());
        }

        Order order = orderRepository.findOrderByBookMessageIdAndUserIdAndStatusIsTrue(bookMessageId.longValue(),userId);

        if (order == null) {
            object.put("ifOrder", false);
            object.put("orderId","");

        }else {
            object.put("ifOrder", true);
            object.put("orderId", order.getOrderId());
        }

        historyRepository.save(history);
        return getSuccess("OK", object, 1);
    }


    /**
     * 向BookMsg表中新增一条图书信息
     * 反馈一条消息
     *
     * @return msg
     */
    @RequestMapping(path = "addBookMsg", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean addBookMsg(BookMessage bookMessage) {
//        bookMessage.setBookMessageId(null);\
        bookMessage.setStatus(true);
        bookMsgRepository.save(bookMessage);
        return getSuccess();
    }

    /**
     * 从BookMsg表中删除几条图书信息
     *
     * @return
     */
    @RequestMapping(path = "deleteBookMsg", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean deleteBookMsg(@RequestBody JSONObject object) {

        if (object == null || !object.containsKey("list"))
            return getFailure("请选择正确的信息");
        List<Integer> list = (List<Integer>) object.get("list");
        for (Integer integer : list) {
            BookMessage bookMessage = new BookMessage();
            bookMessage.setBookMessageId(integer.longValue());
            bookMessage.setStatus(false);
            bookMsgRepository.save(bookMessage);
        }
        return getSuccess();
    }

    /**
     * 修改BookMsg表中一条数据
     *
     * @return private Long bookMessageId;
     * private String name;
     * private Long kindId;
     * private String author;
     * private String publisher;
     * private String introduction;
     * private Boolean status;
     * private Long creatorId;
     * private LocalDateTime createTime;
     * private String isbn;
     */
    @RequestMapping(path = "updateBookMsg", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ReturnBean deleteBookMsg(Long bookMessageId, String name, Long kindId, String author, String publisher, String introduction, String ISBN) {
        Long userId = ShiroUtils.getUserId();
        BookMessage bookMessage = new BookMessage(bookMessageId, name, kindId, author, publisher, introduction, true, userId, LocalDateTime.now(), ISBN);
        bookMsgRepository.save(bookMessage);
        return getSuccess();
    }


    /**
     * 图书借出排行 前十名
     */
    @RequestMapping(path = "listTopTenBook", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean listTopTenBook() {
        List<JSONObject> list = bookMsgRepository.listTopTenBook();
        return getSuccess("OK", list, list.size());
    }


    @RequestMapping(path = "listTopTenCollectionBook", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean listTopTenCollectionBook() {
        List<JSONObject> list = bookMsgRepository.listTopTenCollectionBook();
        return getSuccess("OK", list, list.size());
    }


    /**
     * 选取所有的book MessageId 和 name
     */
    @RequestMapping(path = "listAllBookMsgIdAndName", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean listAllBookMsgIdAndName() {
        List<JSONObject> list = bookMsgRepository.listAllBookMsgIdAndName();
        return getSuccess("OK", list, list.size());
    }

    @RequestMapping(path = "getAuthorAndPublisherByBookMsgId", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean getAuthorAndPublisherByBookMsgId(Long bookMessageId) {
        JSONObject object = bookMsgRepository.getAuthorAndPublisherByBookMsgId(bookMessageId);
        return getSuccess("OK", object, 1);
    }


    /**
     * 图书推荐页面
     */
    @RequestMapping(path = "listBookMsgRandom", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean listBookMsgRandom(int limit) {
        List<JSONObject> list = bookMsgRepository.listBookMsgRandom(limit);
        return getSuccess("OK", list, list.size());
    }

    /**
     * 主页的图书模糊查询页面
     */
    @RequestMapping(path = "listBookMsgHomePage", method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean listBookMsgHomePage(String unionSearch) {
        List<JSONObject> list = bookMsgRepository.listBookMsgHomePage(unionSearch);
        return getSuccess("OK", list, list.size());
    }

}
