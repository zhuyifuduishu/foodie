package com.whu.controller;

import com.whu.pojo.Users;
import com.whu.pojo.bo.UserBO;
import com.whu.service.UserService;
import com.whu.utils.CookieUtils;
import com.whu.utils.JsonUtils;
import com.whu.utils.MD5Utils;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="注册登录",tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    public UserService userService;

    @ApiOperation(value = "用户名是否存在",notes ="用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameExist")
    public Result usernameExist(@RequestParam String username){
        //1.判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return Result.errorMsg("用户名不能为空");
        }

        //2.查找注册的用户名是否存在
        boolean exist = userService.queryUsernameExist(username);
        if (exist) {
            return Result.errorMsg("用户民已经存在");
        } else {
            //3.请求成功，用户名没有重复
            return Result.ok();
        }
    }

    @ApiOperation(value = "用户注册",notes ="用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public Result regist(@RequestBody UserBO userBO,
                         HttpServletRequest request, HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        //0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)) {
            return Result.errorMsg("用户名或密码不能为空");
        }

        //1.查询用户名是否存在
        boolean exist = userService.queryUsernameExist(username);
        if (exist) {
            return Result.errorMsg("用户民已经存在");
        }

        //2.密码长度不能少于6位
        if (password.length() < 6) {
            return Result.errorMsg("密码长度不能小于6");
        }

        //3.判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return Result.errorMsg("两次密码输入不一致");
        }

        //4.实现注册
        Users userResult = userService.createUser(userBO);

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult),true);

        return Result.ok();
    }

    @ApiOperation(value = "用户登录",notes ="用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public Result login(@RequestBody UserBO userBO,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //0.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return Result.errorMsg("用户名或密码不能为空");
        }

        //1.实现登录
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return Result.errorMsg("用户名或密码不正确");
        }

        userResult = setNullProperty(userResult);

        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult),true);

        //TODO 生成用户token，存入redis会话
        //TODO 同步购物车数据

        return Result.ok(userResult);
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);

        return userResult;
    }

    @ApiOperation(value = "用户退出",notes ="用户退出",httpMethod = "POST")
    @PostMapping("/logout")
    public Result logout(@RequestParam String userId,
                         HttpServletRequest request, HttpServletResponse response){

        //清空cookie信息
        CookieUtils.deleteCookie(request, response, "user");

        //TODO 用户退出登录，需要清空购物车
        //TODO 分布式会话需要清空用户数据

        return Result.ok();
    }

}
