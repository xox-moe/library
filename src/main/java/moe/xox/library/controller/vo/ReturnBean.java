package moe.xox.library.controller.vo;

/**
 * 标准返回
 * @Author: wangshaowei
 * @Date: Created in 20:58 2018/4/6
 * @ModifiedBy:
 */
public class ReturnBean {
    public boolean success=true;//是否成功
    public String message="";//提示消息
    public int totalCount=0;//总数
    public Object data;//数据

    public ReturnBean(boolean success,String message,Object data,int totalCount){
        this.success=success;
        this.message=message;
        this.data=data;
        this.totalCount=totalCount;
    }
}
