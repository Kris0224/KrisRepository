package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
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

import java.util.List;

/**
 * KRIS
 */
@Service("brandservice1")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

//   返回的pagereult结果
    PageResult<BrandDTO> pageResult;

    @Override
    public BrandDTO queryByBid(Long bid) {
        return null;
    }

    @Override
    public List<BrandDTO> queryBrandByCid(Long id) {
        return null;
    }

    /**
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

//        分页
//        PageHelper.startPage(page, rows);
//        startindex
        int startIndex = (page - 1) * 5;
//        过滤
//        若搜索关键字字段不为空
        if (StringUtils.isNotBlank(key)) {
//        把key转换为大写
            String UpperKey = key.toUpperCase();

//        排序
//        根据sortby是否为空来判断是否排序
            if (StringUtils.isNotBlank(sortBy)) {
                //查询
                List<Brand> brands = brandMapper.selectBrandbyKey(UpperKey, sortBy,startIndex);
//
            } else {
//
//            没有传递排序的参数,直接根据关键字
                List<Brand> brands = brandMapper.selectBrandbyKey(UpperKey, null,startIndex);

//            健壮性判断
//            若未查询到 抛出异常brand  not found exception
                if (brands == null) {
                    throw new LeyouException(ExceptionEnums.BRAND_NOT_FOUND);
                }
//            查询总条数
                long totalCount = brandMapper.selectTotalConut();
//            计算总页数
                Integer totolPage = (int) Math.ceil(totalCount / 5);
//            实体类转换
                List<BrandDTO> brandDTOS = BeanHelper.copyWithCollection(brands, BrandDTO.class);

//            创建返回的实体类 pageResult
                pageResult = new PageResult<>(totalCount,totolPage, brandDTOS);
            }

        }
//        返回pageresult实体类
        return pageResult;
    }

    @Override
    public void addBrand(BrandDTO brandDTO, List<Long> cids) {

    }


}