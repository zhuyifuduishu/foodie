package com.whu.pojo.vo;

import com.whu.pojo.Items;
import com.whu.pojo.ItemsImg;
import com.whu.pojo.ItemsParam;
import com.whu.pojo.ItemsSpec;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 商品详情VO
 * @author Created By MrXi on 2019/12/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

}
