package com.whu.service.center;

import com.whu.pojo.Users;
import com.whu.pojo.bo.center.CenterUserBO;


public interface CenterUserService {
    /*
    * 根据用户id查询用户信息
    * */
    public Users queryUserInfo(String userId);

    /*
     *修改用户信息
     * */
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /*
     * 用户头像更新
     * */
    public Users updateUserFace(String userId, String faceUrl);

}
