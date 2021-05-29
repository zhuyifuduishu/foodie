package com.whu.service;

import com.whu.pojo.UserAddress;
import com.whu.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    /*
     * 查询所有地址
     * */
    public List<UserAddress> queryAll(String userId);

    /*
     * 用户地址
     * */
    public void addNewUserAddress(AddressBO addressBO);
}
