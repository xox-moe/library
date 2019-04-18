package moe.xox.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TempleController {


    @RequestMapping("good")
    public  String good(){
        return "good";
    }
    @RequestMapping("bookMsgDetail")
    public  String bookMsgDetail(){
        return "bookMsgDetail";
    }
    @RequestMapping("index")
    public  String index(){
        return "index";
    }
}
