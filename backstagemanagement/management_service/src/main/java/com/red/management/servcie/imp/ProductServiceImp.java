package com.red.management.servcie.imp;

import com.red.management.mapper.ProductMapper;
import com.red.management.servcie.ProductService;
import com.red.manamgement.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional //开启事务管理
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductMapper productMapper;

    /**
     * 查询所有用户
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findAll() throws Exception {
        return null;
    }

    /**
     * 保存用户
     * @param product
     */
    @Override
    public void save(Product product) {
        productMapper.save(product);
    }

    /**
     * 根据id查询产品信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Product findById(String id) throws Exception {
        return null;
    }
}
