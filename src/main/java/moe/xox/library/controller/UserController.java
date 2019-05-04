package moe.xox.library.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.CollectionRepository;
import moe.xox.library.dao.UserRepository;
import moe.xox.library.dao.UserRoleRepository;
import moe.xox.library.dao.entity.Collection;
import moe.xox.library.dao.entity.User;
import moe.xox.library.dao.entity.UserRole;
import moe.xox.library.project.FILE_PATH;
import moe.xox.library.utils.ImageUtil;
import moe.xox.library.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("yonghuguanli")
public class UserController extends BaseController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    CollectionRepository collectionRepository;

    /**
     * 分页!
     * 返回User表中信息
     * method = RequestMethod.GET
     *
     * @return json
     */
    @RequestMapping(method = RequestMethod.GET)
    public ReturnBean listAllUser(Integer page, Integer limit,
                                  @RequestParam(value = "userId", required = false, defaultValue = "") String userId,
                                  @RequestParam(value = "realName", required = false, defaultValue = "") String realName,
                                  @RequestParam(value = "nickName", required = false, defaultValue = "") String nickName,
                                  @RequestParam(value = "email", required = false, defaultValue = "") String email,
                                  @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
                                  @RequestParam(value = "department", required = false, defaultValue = "") String department,
                                  @RequestParam(value = "major", required = false, defaultValue = "") String major,
                                  @RequestParam(value = "roleId", required = false, defaultValue = "") String roleId) {

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<JSONObject> userPage = userRepository.listAllUser(userId, realName, nickName, email, grade, department, major, roleId,pageable);
        List<JSONObject> userList = userPage.getContent();
        for (JSONObject object : userList) {
            String birrthday = object.get("birthday") == null ? "空" : (object.get("birthday").toString().substring(0, 10));
            object.put("birthday", birrthday);

        }
        return getSuccess("success", userList, userPage.getTotalElements());
    }

    /**
     * 向User表中新增一条用户信息
     * 反馈一条消息
     *
     * @return msg
     * <p>
     * <p>
     * private Long userId;
     * private String email;
     * private String nickName;
     * private String password;
     * //  private Long roleId;
     * private LocalDate birthday;
     * private String realName;
     * private Long grade;
     * private String department;
     * private String major;
     * private Long sex;
     */
    @RequestMapping(path = "addUser", method = RequestMethod.POST)
    public ReturnBean addUser(String email, String nickName, String password, String birthday, String realName, Long grade, String department, String major, Long sex, Long roleId, @RequestParam(value = "img", required = false) MultipartFile file) {

        birthday += " 00:00:00";
//        LocalDateTime.parse(birthday).toLocalDate();

        String fileName = "";
        if (file != null)
            fileName = ImageUtil.saveFile(file, FILE_PATH.IMG_PATH);

        User user = new User(null, email, nickName, fileName, password, LocalDateTime.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate(), realName, grade, department, major, sex);
        user = userRepository.save(user);
        UserRole userRole = new UserRole(user.getUserId(), roleId, true);
        userRoleRepository.save(userRole);
        return getSuccess("success");
    }

    /**
     * 从User表中删除几条用户信息
     *
     * @return
     */
    @RequestMapping(path = "deleteUser", method = RequestMethod.POST)
    public ReturnBean deleteUser(@RequestBody JSONObject object) {

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
     * 修改用户信息
     *
     * @param userId     用户ID
     * @param roleId     角色ID
     * @param email      邮箱
     * @param nickName   昵称
     * @param password   密码
     * @param birthday
     * @param realName
     * @param grade
     * @param department
     * @param major
     * @param sex
     * @return
     */
    @RequestMapping(path = "updateUser", method = RequestMethod.POST)
    @Transactional
    public ReturnBean updateUser(Long userId, Long roleId, String email, String nickName, String password, String birthday, String realName, Long grade, String department, String major, Long sex) {
        birthday += " 00:00:00";
        User oldUser = userRepository.findByUserId(userId);
        User user = new User(userId, email, nickName, oldUser.getImgName(), oldUser.getPassword(), LocalDateTime.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate(), realName, grade, department, major, sex);
//        userRepository.updateUserRole( userId,  roleId);

        if (password != null || !password.equals(""))
            user.setPassword(password);
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

    @RequestMapping(path = "changeImg", method = RequestMethod.POST)
    @Transactional
    public ReturnBean changeImg(@RequestParam MultipartFile file) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        String fileName = ImageUtil.saveFile(file, FILE_PATH.IMG_PATH);
        user.setImgName(fileName);
        userRepository.save(user);
        return getSuccess("OK", fileName, 1);
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
     * 获取当前用户的信息
     *
     * @return
     */
    @RequestMapping("getCurrentUserInfo")
    public ReturnBean getCurrentUserInfo() throws IOException {
        Long userId = ShiroUtils.getUserId();
        User user = userRepository.findByUserId(userId);
        JSONObject object = (JSONObject) JSON.toJSON(user);
//        if (user.getImgName() != null && !user.getImgName().equals(""))
        try {
            object.put("img", ImageUtil.imageToString(FILE_PATH.IMG_PATH + "\\" + user.getImgName()));
        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }
        return getSuccess("OK", object, 1);
    }


    /**
     * 查询用户的收藏
     */
    @RequestMapping("listUserCollection")
    public ReturnBean listUserCollection() {
//        Subject subject = SecurityUtils.getSubject();
//        User user = (User) subject.getSession().getAttribute("user");
//        List<JSONObject> list = userRepository.listUserCollection(user.getUserId());
//        System.out.println(ShiroUtils.getUserId());
        List<JSONObject> list = userRepository.listUserCollection(ShiroUtils.getUserId());
        for (JSONObject object : list) {
            try {
                object.put("img", ImageUtil.imageToString(FILE_PATH.IMG_PATH + "\\" + object.get("imgName")));
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return getSuccess("OK", list, list.size());
    }


    /**
     * 查询用户的浏览历史
     */
    @RequestMapping("listUserHistory")
    public ReturnBean listUserHistory() {
//        Pageable pageable = PageRequest.of(page - 1, limit);
        Long userId = ShiroUtils.getUserId();
        List<JSONObject> list = userRoleRepository.listUserHistory(userId);
        for (JSONObject object : list) {
            try {
                if (object.containsKey("imgName") && !object.get("imgName").equals(""))
                    logger.info("展示的的图片名为:" + (String) object.get("imgName"));
                object.put("img", ImageUtil.imageToString(FILE_PATH.IMG_PATH + "\\" + object.get("imgName")));
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
        }
        return getSuccess("OK", list, list.size());
    }

    /**
     * 收藏一本书
     */
    @RequestMapping("collectionBook")
    public ReturnBean collectionBook(Long bookMessageId) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        Collection oldCollection = collectionRepository.findCollectionByUserIdAndBookMessageId(user.getUserId(), bookMessageId);
        if (oldCollection != null)
            return getSuccess("您已收藏过该书啦");
        Collection collection = new Collection(null, user.getUserId(), bookMessageId, LocalDateTime.now());
        Collection collections = collectionRepository.save(collection);
        return getSuccess("收藏成功", collections, 1);
    }

    /**
     * 删除一本书  会确实是该用户删除自己的书
     *
     * @param collectionId 收藏ID
     * @return 成功或者失败
     */
    @RequestMapping("deleteCollectionById")
    public ReturnBean deleteCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findCollectionByCollectionId(collectionId);
        if (!collection.getUserId().equals(ShiroUtils.getUserId()))
            return getFailure("请确认这是您的收藏,不要尝试删除不属于您的收藏");
        collectionRepository.delete(collection);
        return getSuccess();
    }


    @RequestMapping(path = "updateUserInfoBySelf", method = RequestMethod.POST)
    @Transactional
    public ReturnBean updateUserInfoBySelf(String email, String nickName, String realName, String birthday,
                                           Long grade, String department, String major, Long sex) {
        birthday += " 00:00:00";
        Long userId = ShiroUtils.getUserId();
        User oldUser = userRepository.findByUserId(userId);
        User user = new User(userId, email, nickName, oldUser.getImgName(), oldUser.getPassword(),
                LocalDateTime.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate(),
                realName, grade, department, major, sex);
        userRepository.save(user);
        return getSuccess("success");
    }


}