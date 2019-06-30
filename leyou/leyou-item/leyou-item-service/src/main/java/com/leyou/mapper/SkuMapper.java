package com.leyou.mapper;

import com.leyou.common.mapper.MyMapper;
import com.leyou.entity.Sku;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

//insertlist 新增  相当于 insert into table in (list)
public interface SkuMapper extends MyMapper<Sku>, InsertListMapper<Sku> {
}
