package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookRepository;
import moe.xox.library.dao.NoticeRepository;
import moe.xox.library.dao.entity.BookMessage;
import moe.xox.library.dao.entity.Notice;
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
    public ReturnBean showNoticeManagerTable(Integer page,Integer limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Notice> noticePage = noticeRepository.findAll(pageable);
        List<Notice> noticeList = noticePage.getContent();
        return getSuccess("success", noticeList, noticePage.getTotalElements());
    }

    /**
     * 向Notice表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addNotice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addNotice(Notice notice){
        noticeRepository.save(notice);
        return getSuccess();
    }

    /**
     * 修改Book表中一条数据
     * 从Notice表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteNotice",method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean updateNotice(@RequestBody JSONObject object){

        if(object == null || !object.containsKey("list"))
            return getFailure("请选择正确的信息");
        List<Integer> list = (List<Integer>) object.get("list");
        for (Integer integer : list) {
            Notice notice = new Notice();
            notice.setNoticeId(integer.longValue());
            noticeRepository.delete(notice);
        }
        return getSuccess();
    }

    /**
     * 从Notice表中更新条图书信息
     * @param notice
     * @return
     */
    @RequestMapping(path = "updateNotice",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteNotice(Notice notice){
        noticeRepository.save(notice);
        return getSuccess();
    }

    /**
     * 首页显示当前有效的公告
     */



}
