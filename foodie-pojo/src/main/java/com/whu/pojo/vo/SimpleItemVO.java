package com.whu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 6个最新商品的简单数据类型
 * @author Created By MrXi on 2019/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleItemVO {

    private String itemId;
    private String itemName;
    private String itemUrl;

}
