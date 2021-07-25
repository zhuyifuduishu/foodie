package com.whu.controller;

import com.whu.enums.PayMethod;
import com.whu.pojo.bo.SubmitOrderBO;
import com.whu.service.OrderService;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关",tags = "订单相关的api接口")
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户下单")
    @RequestMapping("/create")
    public Result create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type &&
                submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return Result.errorMsg("支付方式不支持！");
        }

        //1.创建订单
        String orderId=orderService.createOrder(submitOrderBO);

        //2.订单创建后，移除购物车中已经结算（已提交的）商品
        //TODO 整合Redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        //CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);

        //3.向订单中心发送当前订单，用于保存支付中心的订单数据


        return Result.ok();
    }

}
