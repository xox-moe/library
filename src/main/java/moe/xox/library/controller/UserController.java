package moe.xox.library.controller;


import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.UserRepository;
import moe.xox.library.dao.UserRoleRepository;
import moe.xox.library.dao.entity.User;
import moe.xox.library.dao.entity.UserRole;
import net.bytebuddy.description.field.FieldDescription;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("yonghuguanli")
public class UserController extends BaseController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    /**
     * 分页!
     * 返回User表中信息
     * method = RequestMethod.GET
     *
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ReturnBean listAllUser(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> userPage = userRepository.listAllUser(pageable);
        List<JSONObject> userList = userPage.getContent();
        for (JSONObject object : userList) {
            String birrthday = object.get("birthday") == null ? "空" : ( object.get("birthday").toString().substring(0, 10));
            object.put("birthday", birrthday);

        }
        return getSuccess("success", userList, userPage.getTotalElements());
    }

    /**
     * 向User表中新增一条用户信息
     * 反馈一条消息
     *
     * @return msg
     *
     *
     *   private Long userId;
     *   private String email;
     *   private String nickName;
     *   private String password;
     * //  private Long roleId;
     *   private LocalDate birthday;
     *   private String realName;
     *   private Long grade;
     *   private String department;
     *   private String major;
     *   private Long sex;
     */
    @RequestMapping(path = "addUser", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean addUser(String email,String nickName,String password,String birthday,String realName,Long grade,String department,String major,Long sex) {

        birthday += " 00:00:00";
//        LocalDateTime.parse(birthday).toLocalDate();
        User user = new User(null, email, nickName, password, LocalDateTime.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate(), realName, grade, department, major, sex);
        userRepository.save(user);
        return getSuccess("success");
    }

    /**
     * 从User表中删除几条用户信息
     *
     * @return
     */
    @RequestMapping(path = "deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ReturnBean deleteBook(@RequestBody JSONObject object) {

        if (object == null || !object.containsKey("list"))
            return getFailure("请选择正确的信息");
        List<Integer> list = (List<Integer>) object.get("list");
        for (Integer id : list) {
            User user = new User();
            user.setUserId(id.longValue());
            userRepository.delete(user);
        }

        return getSuccess("success");
    }

    /**
     * 修改User表中一条数据
     *
     * @param user
     * @return
     */
    @RequestMapping(path = "updateUser", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ReturnBean updateBook(Long userId,Long roleId,String email,String nickName,String password,String birthday,String realName,Long grade,String department,String major,Long sex) {
        birthday += " 00:00:00";
        User oldUser = userRepository.findByUserId(userId);
        User user = new User(userId, oldUser.getEmail(), nickName, oldUser.getPassword(), LocalDateTime.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate(), realName, grade, department, major, sex);
//        userRepository.updateUserRole( userId,  roleId);
        UserRole userRole = userRoleRepository.findUserRoleByUserId(userId);
        if (userRole == null)
            userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setUserId(userId);
        userRole.setStatus(true);
        userRoleRepository.save(userRole);
        userRepository.save(user);
        return getSuccess("success");
    }


    /**
     * 通过用户ID查询用户的详细信息
     */
    public ReturnBean getUserInfoByUserId(Long userId) {
        User user = userRepository.findByUserId(userId);
        return getFailure("success", user);
    }

    /**
     * 通过用户邮箱查询用户
     */
    public ReturnBean getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return getFailure("success", user);
    }


    /**
     * 查询用户的收藏
     */
    public ReturnBean listUserCollection() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        List<JSONObject> list = userRepository.listUserCollection(user.getUserId());
        return getSuccess("OK", list, list.size());
    }


    /**
     * 查询用户的浏览历史
     */


    /**
     *
     */

}
