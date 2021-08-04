package com.whu.service;

import com.whu.pojo.OrderStatus;
import com.whu.pojo.bo.ShopCartBO;
import com.whu.pojo.bo.SubmitOrderBO;
import com.whu.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {
    /*
     *创建订单相关信息
     * */
    public OrderVO createOrder(List<ShopCartBO> shopcartList,SubmitOrderBO submitOrderBO);

    /*
     *修改订单状态
     * */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /*
     *查询订单状态
     * */
    public OrderStatus queryOrderStatusInfo(String orderId);


    /*
     * 关闭超市未支付订单
     * */
    public void closeOrder();
}
