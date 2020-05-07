package com.weimob.gateway.goods;

import com.weimob.goods.api.CategoryService;
import com.weimob.goods.entity.Category;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-12:11
 */
@RestController
@RequestMapping("/item/category")
public class CategoryController {
    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    /**
     * 根据父节点id查询商品分类
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid")Long pid){
        return ResponseEntity.ok(categoryService.queryCategoryByPid(pid));
    }

    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryByIds(ids));
    }

}

