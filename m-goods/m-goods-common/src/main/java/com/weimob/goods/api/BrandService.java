package com.weimob.goods.api;

import com.weimob.common.vo.PageResult;
import com.weimob.goods.entity.Brand;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-10:47
 */
public interface BrandService {
    //分页查询品牌
    PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    //新增品牌
    void saveBrand(Brand brand, List<Long> cids);

    //根据id查询品牌
    Brand queryById(Long id);

    //根据分类id查询下面的品牌列表
    List<Brand> queryBrandByCid(Long cid);

    //根据id集合查询品牌列表
    List<Brand> queryByIds(List<Long> ids);

    //修改品牌
    void updateBrand(Brand brand,List<Long> cids);

    //删除品牌
    void deleteBrand(Long bid);

}
