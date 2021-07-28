package com.whu.controller;


import java.io.File;

public class BaseController {

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

}
