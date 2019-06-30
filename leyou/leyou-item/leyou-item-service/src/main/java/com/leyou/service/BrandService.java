package com.leyou.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.dto.BrandDTO;

import java.util.List;

public interface BrandService {

    PageResult<BrandDTO> queryBrandByPage(Integer page, Integer rows, String key, String sortBy, Boolean desc);

    void addBrand(BrandDTO brandDTO, List<Long> cids);

    BrandDTO queryByBid(Long bid);

    List<BrandDTO> queryBrandByCid(Long id);
}
