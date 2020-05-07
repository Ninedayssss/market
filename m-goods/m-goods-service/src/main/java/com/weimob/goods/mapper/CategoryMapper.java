package com.weimob.goods.mapper;

import com.weimob.goods.entity.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author itsNine
 * @create 2020-04-15-10:54
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category,Long> {
}
