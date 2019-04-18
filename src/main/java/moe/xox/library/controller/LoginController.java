package moe.xox.library.controller;

import io.swagger.annotations.ApiOperation;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.entity.User;
import moe.xox.library.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends BaseController{
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

//    Subject


    @RequestMapping(value = "login",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView login(String userName, String password){
        ModelAndView modelAndView = new ModelAndView();
        logger.info("请求登陆 userName："+userName+" 密码："+password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(userName,password));
        }catch (UnknownAccountException ex){
            logger.info("登录失败 无此账号");

            modelAndView.addObject("userName", "userName");
            modelAndView.addObject("password", "password");
            modelAndView.setViewName("login");
//            return getFailure("登录失败 无此账号");
//            return "login";
        } catch (LockedAccountException loex){
            logger.info("登录失败，此账号已被锁定");
        }
        logger.info("登陆成功");
        modelAndView.setViewName("redirect:/index");
//        return "redirect:/index";
        return modelAndView;
//        return getSuccess("success");
    }



    @RequestMapping(value = "hasRole",method = {RequestMethod.POST,RequestMethod.GET})
    public ReturnBean hsaRoleTest(String roleName){
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole(roleName))
            return getSuccess("有这个角色");
        return getFailure("没有这个角色");
    }

//    public List<String> getAllRoleNameForUser(){
//        Subject subject = SecurityUtils.getSubject();
//        subject.get
//    }

    @ApiOperation(value = "通过邮件获取用户信息",notes = "test")
    @RequestMapping(value = "getUserByEmail",method = RequestMethod.GET)
    public ReturnBean getUserByEmail(@RequestParam("email") String email){
        logger.info("email:"+email);
        User user = userService.findUserByEmail(email);
        return getSuccess("success", user, 1);
    }

//    @ApiOperation(value = "通过昵称获取用户列表",notes = "记得后面把密码去掉")
//    @RequestMapping(value = "getUserByNickname",method = RequestMethod.GET)
//    public ReturnBean listUserByNickname(String nickname){
//        List<User> list = userService.findUserByNickname(nickname);
//        return getSuccess("success", list, list.size());
//    }

}
