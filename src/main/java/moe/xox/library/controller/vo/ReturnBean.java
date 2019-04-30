package moe.xox.library.controller.vo;

/**
 * 标准返回
 * @Author: wangshaowei
 * @Date: Created in 20:58 2018/4/6
 * @ModifiedBy:
 */
public class ReturnBean {
    public boolean success=true;//是否成功
    public String msg ="";//提示消息
    public int count =0;//总数
    public Object data;//数据
    public int code;
    public ReturnBean(boolean success, String msg, Object data, int totalCount, int code){
        this.success=success;
        this.msg = msg;
        this.data=data;
        this.count =totalCount;
        this.code=code;
    }
}
