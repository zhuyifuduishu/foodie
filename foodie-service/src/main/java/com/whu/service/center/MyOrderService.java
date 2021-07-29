package com.whu.service.center;

import com.whu.pojo.Orders;
import com.whu.pojo.vo.OrderStatusCountsVO;
import com.whu.utils.PagedGridResult;

public interface MyOrderService {

    /*
     * 查询我的订单列表
     * */
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus,
                                         Integer page, Integer pageSize);


    /*
     * 订单状态-->商家发货
     * */
    public void updateDeliverOrderStatus(String orderId);

    /*
     *查询我的订单
     * */

    public Orders queryMyOrder(String userId, String orderId);

    /*
     *更新是更新订单状态表，没有关联用户id
     * */

    public boolean updateReceiveOrderStatus(String orderId);

    /*
     *逻辑删除
     * */

    public boolean deleteOrder(String userId, String orderId);

    /*
     * 查询用户订单数
     * */

    public OrderStatusCountsVO getOrderStatus(String userId);

    /*
    *获得分页的订单动向
    * */

    public PagedGridResult getOrderTrend(String userId,
                                         Integer page, Integer pageSize);


}
