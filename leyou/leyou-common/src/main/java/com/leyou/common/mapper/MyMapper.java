package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

@RegisterMapper
public interface MyMapper<T>  extends Mapper<T>, IdListMapper<T, Long>, IdsMapper<T> {
}
