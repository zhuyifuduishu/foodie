package com.whu.service.center;

import com.whu.pojo.OrderItems;
import com.whu.pojo.bo.center.OrderItemsCommentBO;
import com.whu.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {
    /*
     * 根据订单id查询关联的商品
     * */
    public List<OrderItems> queryPendingComment(String orderId);

    /*
     * 保存用户的评论
     * */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /*
     *历史评价查询
     * */
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);

}
