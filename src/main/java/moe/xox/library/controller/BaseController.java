package moe.xox.library.controller;

import moe.xox.library.controller.vo.ReturnBean;
import org.springframework.stereotype.Controller;


@Controller
public class BaseController {
    public static final String SUFFIX = ".page";
    public static final String ACTION = ".action";

    /**
     * 获取当前用户
     *
     * @param session
     * @return
     */
//    public LoginUser getLoginUser(HttpSession session) {
//        LoginUser loginUser = (LoginUser) session.getAttribute(SessionParam.LOGIN_USER);
//        return loginUser;
//    }

    /**
     * 返回成功
     *
     * @param msg
     * @return
     */
    public ReturnBean getSuccess(String msg) {
        return new ReturnBean(true, msg == null ? "" : msg, null, 0);
    }

    /**
     *
     * @return success
     */
    public ReturnBean getSuccess() {
        return new ReturnBean(true, "success", null, 0);
    }

    /**
     * 返回成功
     *
     * @param msg
     * @param data
     * @param totalCount
     * @return
     */
    public ReturnBean getSuccess(String msg, Object data, int totalCount) {
        return new ReturnBean(true, msg == null ? "" : msg, data, totalCount);
    }

    public ReturnBean getSuccess(String msg, Object data, long totalCount) {
        return new ReturnBean(true, msg == null ? "" : msg, data, (int) totalCount);
    }


    /**
     * 返回失败
     *
     * @param msg
     * @return
     */
    public ReturnBean getFailure(String msg, Object data, int totalCount) {
        return new ReturnBean(true, msg == null ? "" : msg, data, totalCount);
    }

    /**
     * 返回失败
     *
     * @param msg
     * @return
     */
    public ReturnBean getFailure(String msg) {
        return new ReturnBean(false, msg == null ? "" : msg, null, 0);
    }

    /**
     * 返回失败
     *
     * @param msg
     * @param data
     * @return
     */
    public ReturnBean getFailure(String msg, Object data) {
        return new ReturnBean(false, msg == null ? "" : msg, data, 0);
    }
}
