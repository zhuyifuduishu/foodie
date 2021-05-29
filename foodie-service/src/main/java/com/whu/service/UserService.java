package com.whu.service;

import com.whu.pojo.Users;
import com.whu.pojo.bo.UserBO;

public interface UserService {
    //判断用户名是否存在
    public boolean queryUsernameExist(String username);

    //创建用户
    public Users createUser(UserBO userBO);

    //检索用户名和密码是否匹配，用于登录
    public Users queryUserForLogin(String username,String password);
}
