package com.weimob.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weimob.common.enums.ExceptionEnum;
import com.weimob.common.exception.MarketException;
import com.weimob.common.vo.PageResult;
import com.weimob.goods.api.GoodsService;
import com.weimob.goods.entity.Category;
import com.weimob.goods.entity.Spu;
import com.weimob.goods.mapper.SpuMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author itsNine
 * @create 2020-04-15-11:10
 */
@Service(version = "1.0.0")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private BrandServiceImpl brandService;


    /**
     * 添加Spu
     * @param spu
     */
    @Override
    public void saveGoods(Spu spu) {
        //新增spu
        spu.setSaleable(true);
        spu.setValid(true);
        int count = spuMapper.insert(spu);
        if (count != 1){
            throw new MarketException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

    }



    /**
     * 商品下架
     * @param id
     */
    @Override
    public void goodsSoldOut(Long id) {
        //根据id查找出spu
        Spu oldSpu = spuMapper.selectByPrimaryKey(id);
        //设置下架
        oldSpu.setSaleable(false);
        //更新
        spuMapper.updateByPrimaryKeySelective(oldSpu);
    }

    /**
     * 商品上架
     * @param id
     */
    @Override
    public void goodsSoldUp(Long id) {
        Spu oldSpu = spuMapper.selectByPrimaryKey(id);
        oldSpu.setSaleable(true);
        spuMapper.updateByPrimaryKeySelective(oldSpu);
    }

    /**
     * 逻辑删除商品
     * @param id
     */
    @Override
    public void deleteGoods(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setValid(false);
        spuMapper.updateByPrimaryKeySelective(spu);

    }

    /**
     * 修改商品
     * @param spu
     */
    @Override
    public void updateGoods(Spu spu) {
        if (spu.getId() == null){
            throw new MarketException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1){
            throw new MarketException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }


    }

    /**
     * 分页查询Spu
     * @param page
     * @param rows
     * @param salebale
     * @param key
     * @param valid
     * @return
     */
    @Override
    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean salebale, String key, Boolean valid) {
        //分页
        PageHelper.startPage(page,rows);

        //过滤条件设置
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //搜索字段过滤
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title","%"+key+"%");
        }
        //上下架过滤
        if (salebale != null){
            criteria.andEqualTo("saleable",salebale);
        }
        //是否删除过滤
        criteria.andEqualTo("valid",valid);

        //设置排序
        example.setOrderByClause("id DESC");
        //查询
        List<Spu> spus = spuMapper.selectByExample(example);



        //处理品牌和分类名称
        loadCategoryAndBrandName(spus);


        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);


        return new PageResult<>(info.getTotal(),spus);

    }

    /**
     * 处理分类和品牌名称
     * @param spus
     */
    private void loadCategoryAndBrandName(List<Spu> spus){
        for (Spu spu : spus) {
            //处理分类名称
            List<String> categoryNames = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(categoryNames,"/"));
            //处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }



    /**
     * 根据id查询spu
     * @param id
     * @return
     */
    @Override
    public Spu querySpuById(Long id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null){
            throw new MarketException(ExceptionEnum.GOODS_NOT_FOND);
        }

        return spu;
    }

    /**
     * 根据spu的id集合查询spu列表
     * @param ids
     * @return
     */
    @Override
    public List<Spu> querySpuListByIds(List<Long> ids) {
        List<Spu> spus = spuMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(spus)){
            throw new MarketException(ExceptionEnum.GOODS_NOT_FOND);
        }
        return spus;
    }

}
