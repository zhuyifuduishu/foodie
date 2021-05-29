package com.whu.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created By MrXi on 2020/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantOrdersVO {

    /**商户订单号*/
    private String merchantOrderId;
    /**商户方的发起用户的用户主键id*/
    private String merchantUserId;
    /**实际支付总金额（包含商户所支付的订单费邮费总额）*/
    private Integer amount;
    /**支付方式 1:微信   2:支付宝*/
    private Integer payMethod;
    /**支付成功后的回调地址（学生自定义）*/
    private String returnUrl;

}