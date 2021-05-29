package com.whu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 二级分类VO
 * @author Created By MrXi on 2019/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    // 三级分类vo list
    private List<SubCategoryVO> subCatList;

}
