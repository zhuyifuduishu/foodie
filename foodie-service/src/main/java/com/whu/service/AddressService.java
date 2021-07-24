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
     * 添加用户地址
     * */
    public void addNewUserAddress(AddressBO addressBO);

    /*
    * 修改用户收货地址
    * */

    public void updateUserAddress(AddressBO addressBO);

    /*
    * 根据用户id，删除对应的用户地址信息
    * */
    public void deleteUserAddress(String userId,String addressId);

    /*
     * 修改用户收货地址
     * */

    public void updateUserAddressToBeDefault(String userId,String addressId);
}
