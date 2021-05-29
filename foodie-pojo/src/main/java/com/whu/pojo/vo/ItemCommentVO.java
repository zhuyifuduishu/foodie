package com.whu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


/**
 * 用于展示商品评价的VO
 * @author Created By MrXi on 2019/12/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommentVO {

    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;

}
