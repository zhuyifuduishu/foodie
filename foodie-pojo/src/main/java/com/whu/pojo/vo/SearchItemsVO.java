package com.whu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用于展示商品搜索列表结果的VO
 * @author Created By MrXi on 2019/12/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemsVO {

    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;

}
