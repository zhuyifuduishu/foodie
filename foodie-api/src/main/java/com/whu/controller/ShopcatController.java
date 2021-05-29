package com.whu.controller;

import com.whu.pojo.bo.ShopCartBO;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接",tags = {"购物车接口相关"})
@RestController
@RequestMapping("/shopcart")
public class ShopcatController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加上商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public Result add(
            @RequestParam String userId,
            @RequestBody ShopCartBO shopCartBO,
            HttpServletRequest request,
            HttpServletResponse response){
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg("");
        }

        //TODO 前端用户在登录的情况下，添加商品到购物车，会在后端同步购物车到redis

        return Result.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public Result del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response){
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)) {
            return Result.errorMsg("参数不能为空");
        }

        //TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return Result.ok();
    }
}
