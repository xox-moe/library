package moe.xox.library.utils;

import moe.xox.library.dao.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiorUtils {


    public static Long getUserId(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        return user.getUserId();
    }

}
