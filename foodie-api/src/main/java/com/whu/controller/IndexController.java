package com.whu.controller;

import com.whu.enums.YesOrNo;
import com.whu.pojo.Carousel;
import com.whu.pojo.Category;
import com.whu.pojo.vo.CategoryVO;
import com.whu.pojo.vo.NewItemsVO;
import com.whu.service.CarouselService;
import com.whu.service.CategoryService;
import com.whu.utils.JsonUtils;
import com.whu.utils.RedisOperator;
import com.whu.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "首页",tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public Result carousel() {

        List<Carousel> list = new ArrayList<>();

        //先从缓存中查询
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)) {
            //从数据库中取
            list = carouselService.queryAll(YesOrNo.YES.type);
            //存入到缓存中
            redisOperator.set("carousel", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(carouselStr,Carousel.class);
        }



        return Result.ok(list);
    }

    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public Result cats() {
        List<Category> list = categoryService.queryAllRootLevelCat();

        return Result.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public Result subCat(
            @ApiParam(name="rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return Result.errorMsg("分类不存在");
        }

        List<CategoryVO> list = new ArrayList<>();
        String catsStr = redisOperator.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.getSubCatList(rootCatId);

            /*
             * 查询的key在redis中不存在，
             * 对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在db上，
             * 造成宕机，从而影响整个系统，
             * 这种现象称之为缓存穿透。
             * 解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             * */
            if (list != null && list.size() > 0) {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list));
            } else {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list), 5 * 60);
            }
        } else {
            list = JsonUtils.jsonToList(catsStr, CategoryVO.class);
        }

        return Result.ok(list);
    }


    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public Result sixNewItems(
            @ApiParam(name="rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return Result.errorMsg("分类不存在");
        }

        List<NewItemsVO> list=categoryService.getSixNewItemsLazy(rootCatId);

        return Result.ok(list);
    }
}
