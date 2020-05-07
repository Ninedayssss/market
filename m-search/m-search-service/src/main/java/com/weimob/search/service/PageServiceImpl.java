package com.weimob.search.service;

import com.weimob.goods.api.BrandService;
import com.weimob.goods.api.CategoryService;
import com.weimob.goods.api.GoodsService;
import com.weimob.goods.entity.Brand;
import com.weimob.goods.entity.Category;
import com.weimob.goods.entity.Spu;
import com.weimob.search.api.PageService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author itsNine
 * @create 2020-04-15-11:26
 */
@Service(version = "1.0.0")
public class PageServiceImpl implements PageService {
    @Reference(version = "1.0.0")
    private GoodsService goodsService;

    @Reference(version = "1.0.0")
    private BrandService brandService;

    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    @Override
    public Map<String, Object> loadModel(Long spuId) {
        HashMap<String, Object> model = new HashMap<>();

        //查询spu
        Spu spu = goodsService.querySpuById(spuId);

        //查询品牌
        Brand brand = brandService.queryById(spu.getBrandId());

        //查询商品分类
        List<Category> categorys = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        model.put("title",spu.getTitle());
        model.put("id",spu.getId());
        model.put("brand",brand);
        model.put("categories",categorys);
        model.put("price",spu.getPrice());
        model.put("displayPrice",formatPrice(spu.getPrice()));

        return model;
    }

    public String formatPrice(Long val){
        String value = val.toString();
        if (value.length() == 0){
            return "0.00";
        }
        if (value.length() == 1){
            return "0.0" + value;
        }
        if (value.length() == 2){
            return "0." + value;
        }

        int i = value.indexOf(".");
        if (i < 0){
            return value.substring(0,value.length() - 2) + "." + value.substring(value.length() - 2);
        }
        String s = value.substring(0, i) + value.substring(i + 1);
        if (i == 1){
            return "0.0" + s;
        }
        if (i == 2){
            return "0." + s;
        }
        if (i > 2){
            return s.substring(0,i-2) + "." + s.substring(i-2);
        }

        String price = value.substring(0, value.length() - 2);
        return price;
    }

}
