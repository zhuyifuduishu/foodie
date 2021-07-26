package com.whu.pojo.vo;

import com.whu.pojo.bo.ShopCartBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author Created By MrXi on 2020/1/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    /**将要移除的已经结算的商品信息*/
    private List<ShopCartBO> toBeRemovedShopCatList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}