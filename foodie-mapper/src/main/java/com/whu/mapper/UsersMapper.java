package com.whu.mapper;

import com.whu.my.mapper.MyMapper;
import com.whu.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper extends MyMapper<Users> {
}