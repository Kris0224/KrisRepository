package com.leyou.service;


import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.dto.SkuDTO;
import com.leyou.item.pojo.dto.SpuDTO;
import com.leyou.item.pojo.dto.SpuDetailDTO;

import java.util.List;

public interface GoodsService {

    PageResult<SpuDTO> querySpuByPage(String key, Integer page, Integer rows, Boolean saleable);

    void addGoods(SpuDTO spuDTO);

    void updateSaleable(Long id, Boolean saleable);

    SpuDetailDTO querySpuDetailBySpuId(Long spuId);

    List<SkuDTO> querySkuBySpuId(Long spuId);

    void updateGoods(SpuDTO spuDTO);
}
