package com.whu.mapper;

import com.whu.my.mapper.MyMapper;
import com.whu.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrdersMapper extends MyMapper<Orders> {
}