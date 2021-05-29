package com.whu.service;

import com.whu.pojo.Carousel;

import java.util.List;

public interface CarouselService {
    /*
    * 查询所有轮播图列表
    * */
    public List<Carousel> queryAll(Integer isShow);
}
