package com.weimob.search.service;

import com.weimob.common.vo.PageResult;
import com.weimob.goods.api.GoodsService;
import com.weimob.goods.entity.Spu;
import com.weimob.search.api.SearchService;
import com.weimob.search.entity.SearchRequest;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author itsNine
 * @create 2020-04-15-11:28
 */
@Service(version = "1.0.0")
public class SearchServiceImpl implements SearchService {
    @Reference(version = "1.0.0")
    private GoodsService goodsService;

    @Override
    public PageResult<Spu> search(SearchRequest request) {
        //搜索条件
        String key = request.getKey();
        int page = request.getPage() - 1;
        int size = request.getSize();

        PageResult<Spu> result = goodsService.querySpuByPage(page, size, true, key, true);

        return result;
    }
}
