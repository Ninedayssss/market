package com.weimob.goods.service;

import com.weimob.common.enums.ExceptionEnum;
import com.weimob.common.exception.MarketException;
import com.weimob.goods.api.CategoryService;
import com.weimob.goods.entity.Category;
import com.weimob.goods.mapper.CategoryMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-11:08
 */
@Service(version = "1.0.0")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点id查询分类集合
     * @param pid
     * @return
     */
    @Override
    public List<Category> queryCategoryByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> list = categoryMapper.select(category);
        if (CollectionUtils.isEmpty(list)){
            throw new MarketException(ExceptionEnum.CATEGORY_NOT_FOND);
        }

        return list;
    }

    /**
     * 根据id集合查询分类集合
     * @param ids
     * @return
     */
    @Override
    public List<Category> queryByIds(List<Long> ids) {
        List<Category> list = categoryMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)){
            throw new MarketException(ExceptionEnum.CATEGORY_NOT_FOND);
        }

        return list;
    }

}
