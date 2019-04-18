package moe.xox.library.controller;


import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.NoticeRepository;
import moe.xox.library.dao.entity.BookMessage;
import moe.xox.library.dao.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("gonggaoguanli")
public class NoticeController extends BaseController {
    @Autowired
    NoticeRepository noticeRepository;

    /**
     * 分页!
     * 返回Notice信息
     * method = RequestMethod.GET
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookManagerTable(){
        return null;
    }

    /**
     * 向Notice表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addNotice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBook(Notice notice){
        return null;
    }

    /**
     * 从Notice表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteNotice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(List<Integer>list){
        return null;
    }

    /**
     * 修改Book表中一条数据
     * @param notice
     * @return
     */
    @RequestMapping(path = "updateNotice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(Notice notice){
        return null;
    }
}
