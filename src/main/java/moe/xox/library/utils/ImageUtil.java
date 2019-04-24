package moe.xox.library.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

public class ImageUtil {


    public static String bastPath = "F:\\library\\image";


    //将图片装换为base64字符串
    public static String imageToString(String filePath) throws IOException {
        if (filePath ==null){
            return null;
        }else if(filePath.equals("")){
            return null;
        }
        String imageFormat = StringUtil.getPrefix(filePath).substring(1);
        File file = new File(filePath);
        if (!file.exists()){
            return null;
        }
        InputStream inputStream = null;
        byte[] bytes = null;
        inputStream = new FileInputStream(file);
        bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        Base64 encoder = new Base64();
        return "data:image/"+ imageFormat+";base64,"+ new String(encoder.encode(bytes)) ;
    }


    /**
     * 将多个图片装换为base64字符串
     * @param filePaths  图片地址数组
     * @return  返回字符串（用'^'分割）
     * @throws IOException
     */
    public static StringBuffer imageArrayToString(String[] filePaths) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0;i<filePaths.length;i++){
            if (i==0){
                String s = imageToString(filePaths[i]);
                if (s!=null){
                    stringBuffer.append(s);
                }
            }else {
                String s = imageToString(filePaths[i]);
                if (s!=null){
                    stringBuffer.append("^");
                    stringBuffer.append(s);
                }
            }
        }
        return stringBuffer;
    }

    /**
     * 将多个图片装换为base64字符串
     * @param filePaths  图片地址数组
     * @return  返回字符串（用'^'分割）
     * @throws IOException
     */
    public static StringBuffer imageArrayToString(List<String> filePaths) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0;i<filePaths.size();i++){
            if (i==0){
                String s = imageToString(filePaths.get(i));
                if (s!=null){
                    stringBuffer.append(s);
                }
            }else {
                String s = imageToString(filePaths.get(i));
                if (s!=null){
                    stringBuffer.append("^");
                    stringBuffer.append(s);
                }
            }
        }
        return stringBuffer;
    }



}
