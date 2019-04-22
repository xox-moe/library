package moe.xox.library.controller;

import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookKindRepository;
import moe.xox.library.dao.entity.BookKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("kind")
public class KindController extends BaseController {

    @Autowired
    BookKindRepository bookKindRepository;

    /**
     * 分页!
     * 返回Notice信息
     * method = RequestMethod.GET
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listBookKindPage(Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<BookKind> noticePage =   bookKindRepository.findAll(pageable);
        List<BookKind> noticeList = noticePage.getContent();
        return getSuccess("success", noticeList, noticePage.getTotalElements());
    }


    @RequestMapping(value = "listBookKind",method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listBookKind(){
        List<BookKind> noticeList = bookKindRepository.findAll();
        return getSuccess("success", noticeList, noticeList.size());
    }

    /**
     * 向Notice表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addBookKind",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBookKind(BookKind bookKine){
          bookKindRepository.save(bookKine);
        return getSuccess();
    }

    /**
     * 从Notice表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteBookKind",method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean deleteBookKind(@RequestBody JSONObject object){

        if(object == null || !object.containsKey("list"))
            return getFailure("请选择正确的信息");
        List<Integer> list = (List<Integer>) object.get("list");
        for (Integer integer : list) {
            BookKind bookKine = new BookKind();
            bookKine.setKindId(integer.longValue());
              bookKindRepository.delete(bookKine);
        }
        return getSuccess();
    }

    /**
     * 修改Book表中一条数据
     * @param bookKine
     * @return
     */
    @RequestMapping(path = "updateBookKind",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean updateBookKind(BookKind bookKine){
          bookKindRepository.save(bookKine);
        return getSuccess();
    }

}
