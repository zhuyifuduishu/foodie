package com.whu.controller.center;

import com.whu.controller.BaseController;
import com.whu.pojo.vo.OrderStatusCountsVO;
import com.whu.utils.PagedGridResult;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心我的订单", tags = {"用户中心我的订单的相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController extends BaseController {

    @ApiOperation(value = "获得订单状态概况", notes = "获得订单状态概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public Result statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg(null);
        }

        OrderStatusCountsVO result = myOrderService.getOrderStatus(userId);

        return Result.ok(result);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public Result comments(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "评价等级", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSie", value = "商品id", required = false)
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrderService.queryMyOrders(userId, orderStatus, page, pageSize);


        return Result.ok(grid);
    }

    //商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public Result deliver(
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId) throws Exception {
        if (StringUtils.isBlank(orderId)) {
            return Result.errorMsg("订单ID不能为空");
        }

        myOrderService.updateDeliverOrderStatus(orderId);
        return Result.ok();
    }

    //用户确认收货
    @ApiOperation(value = "用户确认收货", notes = "用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public Result confirmReceive(
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId) throws Exception {

        Result checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrderService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return Result.errorMsg("订单确认收获失败！");
        }

        return Result.ok();
    }

    //用户删除订单
    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public Result delete(
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId) throws Exception {

        Result checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = myOrderService.deleteOrder(userId, orderId);
        if (!res) {
            return Result.errorMsg("订单删除失败！");
        }
        return Result.ok();
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public Result trend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSie", value = "商品id", required = false)
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrderService.getOrderTrend(userId, page, pageSize);

        return Result.ok(grid);
    }

    /*//用于验证用户和订单是否有关联关系，避免非法调用
    private Result checkUserOrder(String userId, String orderId) {
        Orders order = myOrderService.queryMyOrder(userId, orderId);
        if (order == null) {
            return Result.errorMsg("订单不存在！");
        }
        return Result.ok();
    }
*/

}
