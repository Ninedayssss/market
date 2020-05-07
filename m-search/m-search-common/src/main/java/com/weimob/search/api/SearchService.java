package com.weimob.search.api;

import com.weimob.common.vo.PageResult;
import com.weimob.goods.entity.Spu;
import com.weimob.search.entity.SearchRequest;

/**
 * @author itsNine
 * @create 2020-04-15-11:25
 */
public interface SearchService {
    PageResult<Spu> search(SearchRequest request);
}
