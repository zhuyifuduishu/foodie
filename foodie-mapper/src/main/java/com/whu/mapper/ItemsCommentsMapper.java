package com.whu.mapper;

import com.whu.my.mapper.MyMapper;
import com.whu.pojo.ItemsComments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {
}