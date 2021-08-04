package com.whu.controller;

import com.whu.pojo.Users;
import com.whu.pojo.bo.ShopCartBO;
import com.whu.pojo.bo.UserBO;
import com.whu.service.UserService;
import com.whu.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api(value="注册登录",tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController extends BaseController {
    @Autowired
    public UserService userService;

    @Autowired
    private RedisOperator redisOperator;

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
        //同步购物车数据
        synchShopcartData(userResult.getId(),request,response);

        return Result.ok(userResult);
    }

    /*
     * 注册登陆成功后，同步cookie和redis中的数据
     * */
    private void synchShopcartData(String userId,HttpServletRequest request,HttpServletResponse response) {
        //1.redis中无数据，如果cookie中的购物车为空，那么此时不做处理；如果cookie中的购物车不为空，此时直接放入redis中

        //2.redis中有数据，如果cookie中的购物车为空，那么直接把redis的购物车覆盖本地cookie；如果cookie中购物车不为空，如果cookie中的某个商品
        // 在redis中存在，以浏览器中的cookie为主，删除redis中的，把cookie中的商品直接覆盖redis（参考京东）

        //3.同步到redis中了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的

        //从redis中获取购物车
        String shopcartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);

        //从cookie中获取购物车
        String shopcartStrCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);


        if (StringUtils.isBlank(shopcartJsonRedis)) {
            //redis为空。cookie不为空，直接cookie中的数据放入redis中
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopcartStrCookie);
            } else {
                //redis为空，cookie为空

            }
        }else {
            //redis不为空，cookie不为空，合并cookie和redis中的购物车的商品数据（同一商品则覆盖redis）
            if (StringUtils.isNotBlank(shopcartStrCookie)) {
                //1.已存在的，把cookie中对应的数量，覆盖redis（参考京东）

                //2.该项商品标记为待删除，统一放入一个待删除的list中

                //3.从cookie中清理所有的待删除list

                //4.合并redis和cookie中的数据

                //5.更新到redis和cookie中

                List<ShopCartBO> shopcartListRedis = JsonUtils.jsonToList(shopcartJsonRedis, ShopCartBO.class);
                List<ShopCartBO> shopcartListCookie = JsonUtils.jsonToList(shopcartStrCookie, ShopCartBO.class);

                //定义一个待删除list
                List<ShopCartBO> pendingDeleteList = new ArrayList<>();

                for (ShopCartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();

                    for (ShopCartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            //覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());

                            //把cookieShopcart放入待删除列表，用于最后的删除合并
                            pendingDeleteList.add(cookieShopcart);
                        }
                    }
                }

                //从现有cookie中删除对应的覆盖过的商品数据,如果不使用removeAll删除，在展示时会出现重复效果
                shopcartListCookie.removeAll(pendingDeleteList);

                //合并两个list
                shopcartListRedis.addAll(shopcartListCookie);

                //更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);
                redisOperator.set(FOODIE_SHOPCART+":"+userId,JsonUtils.objectToJson(shopcartListRedis));

            } else {
                //redis不为空，cookie为空把redis覆盖cookie
                CookieUtils.setCookie(request,response,FOODIE_SHOPCART,shopcartJsonRedis,true);

            }
        }
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

        //用户退出登录，需要清空购物车
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        //TODO 分布式会话需要清空用户数据

        return Result.ok();
    }

}
