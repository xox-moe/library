package moe.xox.library.controller;

import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.project.FILE_PATH;
import moe.xox.library.utils.ImageUtil;
import moe.xox.library.utils.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.swagger.models.refs.RefType.PATH;

@RestController
public class ImageController extends BaseController {


//    public List<Map> getMaterialTypeByCourseId(String courseId, Integer courseTypeId) throws IllegalAccessException, IOException {
//        //获取文件列表
//        List<MaterialType> list = materialTypeRepository.findAllByCourseTypeId(courseTypeId);
//        Course course = courseRepository.getOne(courseId);//获取课程
//        ArrayList<Map> resultList = new ArrayList<>();
//        for (MaterialType materialType : list) {   //添加已上传资料数量
//            Map map = (HashMap<String, Object>)MapUtil.ObjectToMap(materialType);
//            SignMaterial signMaterial = signMaterialRepository.findByCourseIdAndTypeId(courseId, materialType.getId());
//            if (signMaterial==null){
//                map.put("fileCount",0);
//                resultList.add(map);
//            }else {
//                List<String> list1 = StringUtil.cutStringByChar(signMaterial.getFilePath(), ',');
//                String[] strings = new String[list1.size()]; // 获取入径
//                for (int i = 0;i<list1.size();i++){
//                    strings[i] = PATH + course.getUsername() +"/" + courseId+"/" + list1.get(i);
//                }
//                //装换为Base64
//                StringBuffer stringBuffer = ImageUtil.imageArrayToString(strings);
//                map.put("fileCount",list1.size());
//                map.put("base64Image",stringBuffer);
//                map.put("imagePath",signMaterial.getFilePath());
//                resultList.add(map);
//            }
//        }
//        return resultList;
//    }


    @RequestMapping("getImg")
    public ReturnBean getImg(String msg) throws IOException {
        String path = "F:\\library\\image\\123.png";
        String img = ImageUtil.imageToString(path);
        return getSuccess("OK",img,1);
    }

    @RequestMapping("setImg")
    public ReturnBean setImg(@RequestParam("img") MultipartFile file) throws IOException {
        String fileName = ImageUtil.saveFile(file, FILE_PATH.IMG_PATH);
        return getSuccess("OK",fileName,1);
    }
}
