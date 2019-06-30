package com.red.management.servcie;

import com.red.manamgement.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

//    查询所有产品
    List<Product> findAll() throws Exception;

    //根据id查看产品
    Product findById(String id) throws Exception;

//   保存产品
    void save(Product product) throws Exception;


}
