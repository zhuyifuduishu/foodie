package com.whu.controller;

import com.whu.pojo.Items;
import com.whu.pojo.ItemsImg;
import com.whu.pojo.ItemsParam;
import com.whu.pojo.ItemsSpec;
import com.whu.pojo.vo.CommentLevelCountsVO;
import com.whu.pojo.vo.ItemInfoVO;
import com.whu.pojo.vo.ShopCartVO;
import com.whu.service.ItemService;
import com.whu.utils.PagedGridResult;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController {
    @Autowired
    private ItemService itemService;


    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public Result info(
            @ApiParam(name = "itemId", value = "三级分类id", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Result.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return Result.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public Result commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Result.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return Result.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public Result comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSie", value = "商品id", required = false)
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return Result.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);

        return Result.ok(grid);
    }

    @ApiOperation(value = "根据关键字搜索商品列表", notes = "根据关键字搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public Result search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSie", value = "商品id", required = false)
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return Result.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(keywords,sort,page,pageSize);

        return Result.ok(grid);
    }


    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public Result catItems(
            @ApiParam(name = "catId", value = "关键字", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSie", value = "商品id", required = false)
            @RequestParam Integer pageSize) {
        if (catId==null) {
            return Result.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searchItems(catId,sort,page,pageSize);

        return Result.ok(grid);
    }

    //用于用户长时间未登录网站，刷新购物车中的数据（主要是商品数据），
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public Result refresh(
            @ApiParam(name = "itemSpecIds",value = "拼接的规格ids",required = true,example = "1001,1003,1005")
            @RequestParam String itemSpecIds){
        if (StringUtils.isBlank(itemSpecIds)) {
            return Result.ok();
        }

        List<ShopCartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return Result.ok(list);
    }
}
