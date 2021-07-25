package com.whu.service;

import com.whu.pojo.Items;
import com.whu.pojo.ItemsImg;
import com.whu.pojo.ItemsParam;
import com.whu.pojo.ItemsSpec;
import com.whu.pojo.vo.CommentLevelCountsVO;
import com.whu.pojo.vo.ShopCartVO;
import com.whu.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /*
    * 根据商品id查询详情
    * */
    public Items queryItemById(String itemId);

    /*
    * 根据商品id查询商品图片列表
    * */
    public List<ItemsImg> queryItemImgList(String itemId);

    /*
     * 根据商品id查询商品规格
     * */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /*
     * 根据商品id查询商品参数
     * */
    public ItemsParam queryItemParam(String itemId);

    /*
     *根据id查询商品的评价等级数量
     * */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /*
     *根据商品id查询商品评价
     * */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /*
     *根据关键字搜索商品列表
     * */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /*
     *根据分类id搜索商品列表
     * */
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize);

    /*
    *根据规格ids查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
    * */
    public List<ShopCartVO> queryItemsBySpecIds(String specIds);

    /*
     *根据商品规格id获取规格对象的具体信息
     * */
    public ItemsSpec queryItemSpecById(String specId);

    /*
     *根据商品id获得商品图片
     * */
    public String queryItemMainImgById(String itemId);


    /*
    * 减少库存
    * */
    public void decreaseItemSpecStock(String specId,int buyCount);

}
