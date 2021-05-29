package com.whu.pojo.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于创建订单的BO对象
 * @author Created By MrXi on 2020/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;

}
