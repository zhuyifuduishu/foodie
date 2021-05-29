package com.whu.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户新增或修改地址的BO
 * @author Created By MrXi on 2020/1/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBO {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;

}
