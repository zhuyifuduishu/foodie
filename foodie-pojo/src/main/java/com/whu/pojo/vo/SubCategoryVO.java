package com.whu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 三级分类VO
 * @author Created By MrXi on 2019/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryVO {

    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

}
