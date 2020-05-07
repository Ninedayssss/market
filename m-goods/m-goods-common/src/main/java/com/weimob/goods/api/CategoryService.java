package com.weimob.goods.api;

import com.weimob.goods.entity.Category;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-10:48
 */
public interface CategoryService {
    //根据父节点id查询分类集合
    List<Category> queryCategoryByPid(Long pid);

    //根据id集合查询分类集合
    List<Category> queryByIds(List<Long> ids);
}
