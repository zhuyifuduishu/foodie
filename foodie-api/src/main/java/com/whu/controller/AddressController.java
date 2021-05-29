package com.whu.controller;

import com.whu.pojo.UserAddress;
import com.whu.pojo.bo.AddressBO;
import com.whu.service.AddressService;
import com.whu.utils.MobileEmailUtils;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "地址相关",tags = "地址相关的api接口")
@RestController
@RequestMapping("/address")
public class AddressController {

    /*
    * 用户在确认订单页面，可以针对收货地址做如下操作
    * 1.查询用户的所有收货地址
    * 2.新增
    * 3.删除
    * 4.修改
    * 5.设置
    * */

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址", notes = "根据用户id查询收货地址", httpMethod = "POST")
    @PostMapping("/list")
    public Result list(
            @ApiParam(name = "userId",value = "用户id",required = true)
            @RequestParam String userId){
        if (StringUtils.isBlank(userId)) {
            return Result.errorMsg("");
        }

        List<UserAddress> list = addressService.queryAll(userId);

        return Result.ok(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public Result add(@RequestBody AddressBO addressBO){
        Result checkRes = checkAddress(addressBO);

        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.addNewUserAddress(addressBO);
        return Result.ok();
    }

    private Result checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return Result.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return Result.errorMsg("收货人姓名超过12个字符");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return Result.errorMsg("收货人手机不能为空");
        }

        if (mobile.length() != 11) {
            return Result.errorMsg("收货人手机号长度不正确");
        }

        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return Result.errorMsg("收货人手机号不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();

        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return Result.errorMsg("收货地址信息不能为空");
        }

        return Result.ok();
    }
}
