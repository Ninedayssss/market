package com.weimob.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weimob.common.enums.ExceptionEnum;
import com.weimob.common.exception.MarketException;
import com.weimob.common.vo.PageResult;
import com.weimob.goods.api.BrandService;
import com.weimob.goods.entity.Brand;
import com.weimob.goods.mapper.BrandMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itsNine
 * @create 2020-04-15-10:55
 */
@Service(version = "1.0.0")
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //设置分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNoneBlank(key)){
            //过滤条件设置
            example.createCriteria().orLike("name","%"+key+"%").orEqualTo("letter",key.toUpperCase());
        }
        //排序设置
        if (StringUtils.isNotBlank(sortBy)){
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        //查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            throw new MarketException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //System.out.println("brand"+list.size());


        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids  分类id集合
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌
        int count = brandMapper.insert(brand);
        if (count != 1){
            throw new MarketException(ExceptionEnum.BRAND_SAVE_ERROR);
        }

        //新增品牌与分类的中间表
        for (Long cid : cids){
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1){
                throw new MarketException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }

    }

    /**
     * 根据品牌id查询品牌
     * @param id
     * @return
     */
    @Override
    public Brand queryById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand == null){
            throw new MarketException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        return brand;
    }

    /**
     * 根据分类id查找品牌集合
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> list = brandMapper.queryByCategoryId(cid);
        if (CollectionUtils.isEmpty(list)){
            throw new MarketException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        return list;
    }

    /**
     * 根据id集合查找品牌列表
     * @param ids
     * @return
     */
    @Override
    public List<Brand> queryByIds(List<Long> ids) {
        List<Brand> list = brandMapper.selectByIdList(ids);
        if (CollectionUtils.isEmpty(list)){
            throw new MarketException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return list;
    }

    /**
     * 品牌修改
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {

        //先删除中间表数据
        brandMapper.deleteByBrandIdInCategoryBrand(brand.getId());

        //修改品牌
        brandMapper.updateByPrimaryKey(brand);

        //插入中间表数据
        for (Long cid : cids) {
            brandMapper.insertCategoryBrand(cid,brand.getId());
        }

    }

    /**
     * 删除品牌
     * @param bid
     */
    @Override
    public void deleteBrand(Long bid) {
        //维护中间表
        brandMapper.deleteByBrandIdInCategoryBrand(bid);
        //删除品牌
        brandMapper.deleteByPrimaryKey(bid);
    }
}
