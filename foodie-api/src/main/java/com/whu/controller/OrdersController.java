package com.whu.controller;

import com.whu.enums.OrderStatusEnum;
import com.whu.enums.PayMethod;
import com.whu.pojo.OrderStatus;
import com.whu.pojo.bo.SubmitOrderBO;
import com.whu.pojo.vo.MerchantOrdersVO;
import com.whu.pojo.vo.OrderVO;
import com.whu.service.OrderService;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关", tags = "订单相关的api接口")
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

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
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();


        //2.订单创建后，移除购物车中已经结算（已提交的）商品
        //TODO 整合Redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        //CookieUtils.setCookie(request,response,FOODIE_SHOPCART,"",true);

        //3.向订单中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        merchantOrdersVO.setAmount(1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<Result> responseEntity = restTemplate.postForEntity(paymentUrl, entity, Result.class);
        Result paymentResult = responseEntity.getBody();

        if (paymentResult.getStatus() != 200) {
            return Result.errorMsg("支付中心订单创建失败，请联系管理员！");
        }

        return Result.ok(orderId);
    }

    //修改订单状态
    @ApiOperation(value = "用户下单", notes = "用户下单")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }

    //轮询订单的支付状态
    @PostMapping("getPaidOrderInfo")
    public Result getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return Result.ok(orderStatus);

    }
}
