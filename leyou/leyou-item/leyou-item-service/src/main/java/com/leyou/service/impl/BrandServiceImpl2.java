package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LeyouException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.entity.Brand;
import com.leyou.item.pojo.dto.BrandDTO;
import com.leyou.mapper.BrandMapper;
import com.leyou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
 * 通用mapper写法
 */
@Service("brandservice2")
public class BrandServiceImpl2 implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    /**
     * 查询所有品牌业务
     * @param page   当前页，int 默认为 1
     * @param rows   每页大小，int 默认为 5
     * @param key    搜索关键词，String
     * @param sortBy 排序字段，String 默认可以不传
     * @param desc   是否为降序，boolean 默认false为升序
     * @return
     * @sql语句
     * select *
     * from tb_brand
     * where
     * name like ''
     * OR
     * LETTER =''
     * LIMIT 0, 5
     */
    @Override
    public PageResult<BrandDTO> queryBrandByPage(Integer page, Integer rows, String key, String sortBy, Boolean desc) {

        /**
         * 分页助手
         * 当前页数
         * 每页的条数
         */
        PageHelper.startPage(page, rows);

        // 2.关键字查询
        Example example = new Example(Brand.class);

        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%")
                    .orEqualTo("letter", key.toUpperCase());
        }

        // 3.排序
        if (sortBy != null) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }

        // 4.去数据库搜索
        List<Brand> list = brandMapper.selectByExample(example);

        // 健壮性判断
        if (CollectionUtils.isEmpty(list)) {
            throw new LeyouException(ExceptionEnums.BRAND_NOT_FOUND);
        }

        /**5.获取pageinfo对象 且给予查询结果的泛型
         * 通过getTotal和getPages获得同条数和总页数
         */
        PageInfo<Brand> pageInfo = new PageInfo<>(list);

        /**
         * 6.获得总条数
         */
        long total = pageInfo.getTotal();

        /**
         * 7.获得总页数
         */
        int pages = pageInfo.getPages();

        // 8.解析查询类型的实体类的类型的转换
        List<BrandDTO> brandDTOList = BeanHelper.copyWithCollection(list, BrandDTO.class);

        // 9.获得branddto的pageresult实体类的返回对象
        PageResult<BrandDTO> pageResult = new PageResult<>(total, pages, brandDTOList);

       // 10.返回给controller层
        return pageResult;
    }

   /* *//**
     * 新增品牌
     * @param cids
     *//*
    @Override
    public void addBrand(String name, String image, String letter, List<Long> cids) {

        *//**
         * 新增品牌表
         * 返回新增的主键id
         *//*
        int addId = brandMapper.addBrand();

        if (addId==0){
            throw new LeyouException(ExceptionEnums.INSERT_OPERATION_FAIL);
        }

        *//**
         * 新增中间表
         * 获取刚才新增的主键和
         *//*
        int addMiddleBrandResult = brandMapper.addMiddleBrand(addId,cids);

        if (addMiddleBrandResult!=1){
            throw new LeyouException(ExceptionEnums.INSERT_OPERATION_FAIL);
        }

    }*/

    /**
     * 新增品牌
     * @param brandDTO  品牌的实体类
     * @param cids  分类的参数主键cid的数组 cids
     */
    @Transactional
    public void addBrand(BrandDTO brandDTO, List<Long> cids) {
        // 1.新增品牌
        Brand brand = BeanHelper.copyProperties(brandDTO, Brand.class);
        int count = brandMapper.insertSelective(brand);
        if(count != 1){
//            添加失败抛出添加操作失败异常
            throw new LeyouException(ExceptionEnums.INSERT_OPERATION_FAIL);
        }
        // 2.新增中间表
        //从brand对象中获得新增的品牌的主键bid
        Long bid = brand.getId();
        count = brandMapper.insertCategoryBrand(bid, cids);
        if(count != cids.size()){
//            添加失败抛出添加操作失败异常
            throw new LeyouException(ExceptionEnums.INSERT_OPERATION_FAIL);
        }
    }


    /**
     * 根据bid查询品牌
     * @param bid
     * @return
     */
    public BrandDTO queryByBid(Long bid) {
        Brand brand = brandMapper.selectByPrimaryKey(bid);
        if (brand == null) {
            throw new LeyouException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        //返回的实体类的转换brand->branddto
        return BeanHelper.copyProperties(brand, BrandDTO.class);
    }

    /**
     * 根据cid查询brand
     * 根据品牌查询分类
     * @param id
     * @return
     */
    @Override
    public List<BrandDTO> queryBrandByCid(Long id) {

        List<Brand> brands = brandMapper.queryBrandByCid(id);

        List<BrandDTO> brandDTOList = BeanHelper.copyWithCollection(brands, BrandDTO.class);

        return brandDTOList;
    }

}
