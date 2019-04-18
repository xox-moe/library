package moe.xox.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TempleController {
    Logger logger = LoggerFactory.getLogger(TempleController.class);



    @RequestMapping("good")
    public  String good(){
        return "good";
    }

    @RequestMapping("index")
    public  String index(){
        return "index";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        logger.info("重定向到login");
        return "login";
    }

    @RequestMapping(value = "login",method = {RequestMethod.GET})
    public String login(){
//        logger.info("重定向到login");
        return "login";
    }

    @RequestMapping("bookMsgDetail")
    public  String bookMsgDetail(){
        return "bookMsgDetail";
    }

    @RequestMapping("bookDetail")
    public String bookDetail(){
        return "bookDetail";
    }

    @RequestMapping("adviceDetail")
    public String adviceDetail(){
        return "adviceDetail";
    }

}
