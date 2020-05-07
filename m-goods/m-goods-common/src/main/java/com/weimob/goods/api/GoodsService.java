package com.weimob.goods.api;

import com.weimob.common.vo.PageResult;
import com.weimob.goods.entity.Spu;

import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-10:49
 */
public interface GoodsService {
    //增加商品Spu
    void saveGoods(Spu spuVo);

    //下架商品
    void goodsSoldOut(Long id);

    //上架商品
    void goodsSoldUp(Long id);

    //删除商品
    void deleteGoods(Long id);

    //修改商品
    void updateGoods(Spu spu);

    //分页查找Spu
    PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean salebale, String key, Boolean valid);


    //根据id查找Spu
    Spu querySpuById(Long id);

    //根据spu的id集合查询spu列表
    List<Spu> querySpuListByIds(List<Long> ids);

}
