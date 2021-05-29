package com.whu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于展示商品评价数量的vo
 * @author Created By MrXi on 2019/12/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentLevelCountsVO {

    /**总评价数*/
    public Integer totalCounts;
    /**好评数*/
    public Integer goodCounts;
    /**中评数*/
    public Integer normalCounts;
    /**差评数*/
    public Integer badCounts;

}
