package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.BookMsgRepository;
import moe.xox.library.dao.entity.BookMessage;
//import moe.xox.library.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "showBookMsgManagerTable",method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean showBookMsgManagerTable(int page,int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<BookMessage> bookMessagePage = bookMsgRepository.findAll(pageable);
        List<BookMessage> bookMessageList = bookMessagePage.getContent();
        return getSuccess("success", bookMessageList, bookMessagePage.getTotalElements());
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listBookMsgManageInfo(int page,int limit){
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> bookMessagePage = bookMsgRepository.listBookMsgManageInfo(pageable);
        List<JSONObject> bookMessageList = bookMessagePage.getContent();
        return getSuccess("success", bookMessageList, bookMessagePage.getTotalElements());
    }




    /**
     * 向BookMsg表中新增一条图书信息
     * 反馈一条消息
     * @return msg
     */
    @RequestMapping(path = "addBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean addBookMsg(BookMessage bookMessage){
//        bookMessage.setBookMessageId(null);\
        bookMessage.setStatus(true);
        bookMsgRepository.save(bookMessage);
        return getSuccess();
    }

    /**
     * 从BookMsg表中删除几条图书信息
     * @return
     */
    @RequestMapping(path = "deleteBookMsg",method = RequestMethod.POST)
    @ResponseBody
    public  ReturnBean deleteBookMsg(@RequestBody JSONObject object){

        if(object == null || !object.containsKey("list"))
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
     * @param bookMessage
     * @return
     */
    @RequestMapping(path = "updateBookMsg",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public  ReturnBean deleteBookMsg(BookMessage bookMessage){
        bookMsgRepository.save(bookMessage);
        return getSuccess();
    }


    /**
     * 图书借出排行 前十名
     */
    @RequestMapping(path = "listTopTenBook",method = {RequestMethod.GET})
    @ResponseBody
    public ReturnBean listTopTenBook(){
        List<JSONObject> list =  bookMsgRepository.listTopTenBook();
        return getSuccess("OK", list, list.size());
    }

    /**
     * 图书推荐页面
     */


    /**
     * 主页的图书模糊查询页面
     */





    /***
     * 图片查询demo
     */

    /**
     * 获取资料列表
     * @param courseId
     * @param courseTypeId
     * @return
     * @throws IllegalAccessException
     * @throws IOException
     */
    /*public List<Map> getMaterialTypeByCourseId(String courseId, Integer courseTypeId) throws IllegalAccessException, IOException {
        //获取文件列表
        List<MaterialType> list = materialTypeRepository.findAllByCourseTypeId(courseTypeId);
        Course course = courseRepository.getOne(courseId);//获取课程
        ArrayList<Map> resultList = new ArrayList<>();
        for (MaterialType materialType : list) {   //添加已上传资料数量
            Map map = (HashMap<String, Object>)MapUtil.ObjectToMap(materialType);
            SignMaterial signMaterial = signMaterialRepository.findByCourseIdAndTypeId(courseId, materialType.getId());
            if (signMaterial==null){
                map.put("fileCount",0);
                resultList.add(map);
            }else {
                List<String> list1 = StringUtil.cutStringByChar(signMaterial.getFilePath(), ',');
                String[] strings = new String[list1.size()]; // 获取入径
                for (int i = 0;i<list1.size();i++){
                    strings[i] = PATH + course.getUsername() +"/" + courseId+"/" + list1.get(i);
                }
                //装换为Base64
                StringBuffer stringBuffer = ImageUtil.imageArrayToString(strings);
                map.put("fileCount",list1.size());
                map.put("base64Image",stringBuffer);
                map.put("imagePath",signMaterial.getFilePath());
                resultList.add(map);
            }
        }
        return resultList;
    }*/




}
