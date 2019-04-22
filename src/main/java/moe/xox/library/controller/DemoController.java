package moe.xox.library.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import moe.xox.library.controller.vo.ReturnBean;
import moe.xox.library.dao.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

/**
 * Hello world!
 */
@Api(description = "用户接口")
@RestController
@RequestMapping("/demoController")
public class DemoController extends BaseController {

    @ApiOperation(value = "新增用户", notes = "新增注册")
    @RequestMapping(value = "/createUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReturnBean createUser(@RequestBody User user) {
        System.out.println("createUser:::" + user.toString());
        return getSuccess( "新增成功.");
    }

    @ApiOperation(value = "修改用户", notes = "修改用户")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ReturnBean updateUser(@RequestBody User user) {
        System.out.println("updateUser:::" + user.toString());
        return getSuccess("修改成功.");
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ReturnBean deleteUser(@RequestParam("userId") String userId) {
        System.out.println("deleteUser:::" + userId);
        return  getSuccess( "删除成功.");
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    public ReturnBean queryUser(@RequestParam("userId") String userId) {
        System.out.println("queryUser:::" + userId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User(1L, "zjq@qq.com",
                "终焉警钟", "admin", timestamp,
                "admin", 2016L, "计算机", "软件工程", 1L);
        return   getSuccess("success",user,1);
    }

}