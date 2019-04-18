package moe.xox.library.controller;


import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.UserRepository;
import moe.xox.library.dao.entity.Book;
import moe.xox.library.dao.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("yonghuguanli")
public class UserController extends BaseController {
    @Autowired
    UserRepository UserRepository;

    /**
     * 分页!
     * 返回User表中信息
     * method = RequestMethod.GET
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookManagerTable(){
        return null;
    }

    /**
     * 向User表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addUser",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBook(User user){
        return null;
    }

    /**
     * 从User表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(List<Integer>list){
        return null;
    }

    /**
     * 修改User表中一条数据
     * @param user
     * @return
     */
    @RequestMapping(path = "updateUser",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(User user){
        return null;
    }
}
