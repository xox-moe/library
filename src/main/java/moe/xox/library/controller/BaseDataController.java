package moe.xox.library.controller;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookKindRepository;
import moe.xox.library.dao.BookStatusRepository;
import moe.xox.library.dao.entity.BookKind;
import moe.xox.library.dao.entity.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("data")
public class BaseDataController extends BaseController {


    @Autowired
    BookStatusRepository bookStatusRepository;

    @Autowired
    BookKindRepository bookKindRepository;

    @RequestMapping("listAllBookStatus")
    @ResponseBody
    public ReturnBean listAllBookStatus(){
        List<BookStatus> list = bookStatusRepository.findAllOrderByBookStatusIdDesc();
        return getSuccess("OK",list,list.size());
    }

    @RequestMapping("listAllBookKind")
    @ResponseBody
    public ReturnBean listAllBookKind(){
        List<BookKind> list = bookKindRepository.findAllByStatusIsTrue();
        return getSuccess("OK",list,list.size());
    }

    @RequestMapping("listAllRole")
    @ResponseBody
    public ReturnBean listAllRole(){
        List<JSONObject> list = bookKindRepository.listAllRole();
        return getSuccess("OK",list,list.size());
    }







    @RequestMapping("listAllQuality")
    @ResponseBody
    public ReturnBean listAllQuality(){
//        List<BookKind> list = bookKindRepository.();
        //全新 有使用痕迹 磨损 破损 无法使用
        List<JSONObject> list = new ArrayList<>();
        JSONObject object1 = new JSONObject();
        object1.put("qualityId",1);
        object1.put("qualityName","无法使用");
        list.add(object1);
        JSONObject object2 = new JSONObject();
        object2.put("qualityId",2);
        object2.put("qualityName","破损");
        list.add(object2);
        JSONObject object3 = new JSONObject();
        object3.put("qualityId",3);
        object3.put("qualityName","磨损");
        list.add(object3);
        JSONObject object4 = new JSONObject();
        object4.put("qualityId",4);
        object4.put("qualityName","有使用痕迹");
        list.add(object4);
        JSONObject object5 = new JSONObject();
        object5.put("qualityId",5);
        object5.put("qualityName","全新");
        list.add(object5);
        return getSuccess("OK",list,list.size());
    }





}
