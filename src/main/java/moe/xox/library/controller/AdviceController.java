package moe.xox.library.controller;


import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.AdviceRepository;
import moe.xox.library.dao.entity.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("fankuiguanli")
public class AdviceController extends BaseController {
    @Autowired
    AdviceRepository adviceRepository;

    /**
     * 分页!
     * 返回Advice表中信息
     * method = RequestMethod.GET
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookManagerTable(){
        return null;
    }

    /**
     * 向Advice表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addAdvice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBook(Advice advice){
        return null;
    }

    /**
     * 从Advice表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteAdvice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(List<Integer>list){
        return null;
    }

    /**
     * 修改Advice表中一条数据
     * @param advice
     * @return
     */
    @RequestMapping(path = "updateAdvice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBook(Advice advice){
        return null;
    }
}
