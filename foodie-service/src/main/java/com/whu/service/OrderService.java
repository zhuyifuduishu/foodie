package com.whu.service;

import com.whu.pojo.OrderStatus;
import com.whu.pojo.bo.SubmitOrderBO;
import com.whu.pojo.vo.OrderVO;

public interface OrderService {
    /*
     *创建订单相关信息
     * */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /*
     *修改订单状态
     * */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /*
     *查询订单状态
     * */
    public OrderStatus queryOrderStatusInfo(String orderId);

}
