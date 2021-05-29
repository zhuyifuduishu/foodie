package com.whu.mapper;

import com.whu.my.mapper.MyMapper;
import com.whu.pojo.ItemsComments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Mapper
@Repository
public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    void saveComments(Map<String, Object> map);

    //List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}