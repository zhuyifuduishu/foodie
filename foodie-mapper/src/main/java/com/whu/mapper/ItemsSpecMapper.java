package com.whu.mapper;

import com.whu.my.mapper.MyMapper;
import com.whu.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ItemsSpecMapper extends MyMapper<ItemsSpec> {
}