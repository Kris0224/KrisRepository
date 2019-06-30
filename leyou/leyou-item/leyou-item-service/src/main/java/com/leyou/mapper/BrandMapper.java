package com.leyou.mapper;

import com.leyou.entity.Brand;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand>{

//  根据关键字查询
    List<Brand> selectBrandbyKey(@Param("key") String key, @Param("sortby") String sortby, @Param("startIndex") int startIndex);
// 查询总条数
    long selectTotalConut();
// 新增品牌表
    int addBrand();
// 新增中间表
    int addMiddleBrand(@Param("bid") Integer bid, @Param("cids") List<Long> cids);
// 新增中间表
    int insertCategoryBrand(@Param("bid") Long bid, @Param("cids") List<Long> cids);
// 根据brand查询
    List<Brand> queryBrandByCid(@Param("cid") Long cid);
}
