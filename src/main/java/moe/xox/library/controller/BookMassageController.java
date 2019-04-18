package moe.xox.library.controller;


import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.entity.BookMessage;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("tushuxinxiguanli")
public class BookMassageController extends BaseController {
    @Autowired
    BookMsgRepository bookMsgRepository;

    /**
     * 分页!
     * 返回BookMsg表中的图书信息
     * method = RequestMethod.GET
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookMsgManagerTable(){
        return null;
    }

    /**
     * 向BookMsg表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBookMsg(BookMessage bookMessage){
        return null;
    }

    /**
     * 从BookMsg表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBookMsg(List<Integer>list){
        return null;
    }

    /**
     * 修改BookMsg表中一条数据
     * @param bookMessage
     * @return
     */
    @RequestMapping(path = "updateBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBookMsg(BookMessage bookMessage){
        return null;
    }
}
