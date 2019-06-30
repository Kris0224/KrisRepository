package com.red.management.mapper;

import com.red.manamgement.domain.Product;

import java.util.List;

public interface ProductMapper {

//   查询所有产品信息
     List<Product> findAll() throws Exception;
//   根据id产看产品信息
     Product findById(String id) throws Exception;
//    保存产品信息
     void save(Product product);
}
