package com.weimob.gateway.goods;

import com.weimob.common.vo.PageResult;
import com.weimob.goods.api.BrandService;
import com.weimob.goods.entity.Brand;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-12:10
 */
@RestController
@RequestMapping("/item/brand")
public class BrandController {
    @Reference(version = "1.0.0")
    private BrandService brandService;

    /**
     * 分页查询
     *
     * @param page   页码
     * @param rows   每页内容
     * @param sortBy 根据呐列排序
     * @param desc   升降序
     * @param key    搜索关键词
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ) {
        return ResponseEntity.ok(brandService.queryBrandByPage(page, rows, sortBy, desc, key));
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids    分类id
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.saveBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类id查询品牌列表
     *
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(brandService.queryBrandByCid(cid));
    }

    /**
     * 根据品牌id查询
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandService.queryById(id));
    }

    /**
     * 根据id集合查询品牌列表
     *
     * @param ids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(brandService.queryByIds(ids));
    }

    /**
     * 修改品牌
     *
     * @param brand
     * @param cids  分类id
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand, @RequestParam("cids") List<Long> cids) {
        brandService.updateBrand(brand, cids);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 根据品牌id删除品牌
     *
     * @param bid
     * @return
     */
    @DeleteMapping("bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") Long bid) {
        brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}