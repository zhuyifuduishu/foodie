package com.whu.controller;


import com.whu.pojo.Orders;
import com.whu.service.center.MyOrderService;
import com.whu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class BaseController {

    @Autowired
    public MyOrderService myOrderService;

    public static String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    //支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功->支付中心->天天吃货
    //                     |->回调通知的Url
    String payReturnUrl = "http://cv3knv.natappfree.cc/orders/notifyMerchantOrderPaid";

    //用户上传头像的地址
    //public static final String IMAGE_USER_FACE_LOCATION = "E:\\IDEAworkspace\\foodieImages\\faces";
    public static final String IMAGE_USER_FACE_LOCATION = File.separator+"E:"+
                                                            File.separator+"IDEAworkspace"+
                                                            File.separator+"foodieImages"+
                                                            File.separator+"faces";

    //用于验证用户和订单是否有关联关系，避免非法调用
    public Result checkUserOrder(String userId, String orderId) {
        Orders order = myOrderService.queryMyOrder(userId, orderId);
        if (order == null) {
            return Result.errorMsg("订单不存在！");
        }
        return Result.ok(order);
    }

}
