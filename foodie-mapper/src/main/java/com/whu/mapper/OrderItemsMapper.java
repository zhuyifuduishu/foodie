package com.whu.mapper;

import com.whu.my.mapper.MyMapper;
import com.whu.pojo.OrderItems;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderItemsMapper extends MyMapper<OrderItems> {
}