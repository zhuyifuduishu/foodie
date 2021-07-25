package com.whu.service;

import com.whu.pojo.bo.SubmitOrderBO;

public interface OrderService {
    /*
     *创建订单相关信息
     * */
    public String createOrder(SubmitOrderBO submitOrderBO);
}
