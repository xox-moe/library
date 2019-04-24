package moe.xox.library.controller;

import moe.xox.library.controller.vo.ReturnBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageController extends BaseController {

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


    public ReturnBean getImg(String msg) {

        return super.getSuccess(msg);
    }
}
