package com.whu.controller.center;

import com.whu.controller.BaseController;
import com.whu.enums.YesOrNo;
import com.whu.pojo.OrderItems;
import com.whu.pojo.Orders;
import com.whu.pojo.bo.center.OrderItemsCommentBO;
import com.whu.service.center.MyCommentsService;
import com.whu.utils.PagedGridResult;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评价模块", tags = {"用户中心评价模块的相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;



    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public Result deliver(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId){

        //判断用户和订单是否关联
        Result checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        //判断该订单是否已经评价过了，评价过了就不再继续
        Orders myOrder=(Orders)checkResult.getData();
        if (myOrder.getIsComment() == YesOrNo.YES.type) {
            return Result.errorMsg("这笔订单已经评价过了");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return Result.ok(list);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public Result saveList(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        System.out.println(commentList);

        //用户和订单是否关联
        Result checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        //判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return Result.errorMsg("评论内容不能为空");
        }

        myCommentsService.saveComments(orderId, userId, commentList);
        return Result.ok();
    }

    @ApiOperation(value = "查询历史评论", notes = "查询历史评论", httpMethod = "POST")
    @PostMapping("/query")
    public Result comments(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSie", value = "分页的每一页显示的条数", required = false)
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

        PagedGridResult grid = myCommentsService.queryMyComments(userId, page, pageSize);


        return Result.ok(grid);
    }

}
