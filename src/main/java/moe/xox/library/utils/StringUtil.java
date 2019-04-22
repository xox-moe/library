package moe.xox.library.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    /**
     * 截取后缀
     * @param s
     * @return   ".txt"
     */
    public static String getPrefix(String s){
        int i = s.length()-1;
        boolean z = false;
        for(;i>-1;i--){
            if (s.charAt(i)=='.'){
                z = true;
                break;
            }
        }
        if (!z){
            System.out.println("没有后缀名");
            return "";
        }
        return s.substring(i);
    }

    /**
     * 根据字符切分字符串
     * @param s
     * @param c
     * @return
     */
    public static List<String> cutStringByChar(String s,char c){
        int z = 0;
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0;i<s.length();i++){
            if (s.charAt(i) == c  ){
                list.add(s.substring(z,i));
                z = i+1;
            }
        }
        if(z!=0){
            list.add(s.substring(z));
        }
        if (z==0){
            list.add(s);
        }
        return list;
    }

    /**
     * 获取指定字符到源字符串结尾的字符串 33.txt  . -> txt
     * @param s
     * @param c
     * @return
     */
    public static String getEndByChar(String s,char c){
        int i = s.length()-1;
        boolean z = false;
        for(;i>-1;i--){
            if (s.charAt(i)==c){
                z = true;
                break;
            }
        }
        if (!z){
            return "";
        }
        return s.substring(i+1);
    }

    /**
     * 获取元字符开头到指定字符间的字符串
     * @param s
     * @param c
     * @return
     */
    public static String getStartByChar(String s,char c){
        int i = 0;
        boolean z = false;
        for(;i<s.length();i++){
            if (s.charAt(i)==c){
                z = true;
                break;
            }
        }
        if (!z){
            return null;
        }
        return s.substring(0,i);
    }

    /**
     * 获取两个字符之间的字符串
     * @param s
     * @param c1
     * @param c2
     * @return
     */
    public static String getBetweenByChar(String s,char c1,char c2){
        int start = 0,end=0;
        boolean z = false;
        for(int i=0;i<s.length();i++){
            if (s.charAt(i)==c1){
                start = i;
            }
            if (s.charAt(i)==c2){
                end = i;
            }
        }
        return s.substring(start+1,end);
    }
}
