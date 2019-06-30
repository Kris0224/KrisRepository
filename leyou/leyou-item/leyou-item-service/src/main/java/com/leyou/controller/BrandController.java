package com.leyou.controller;


import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.dto.BrandDTO;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    @Qualifier("brandservice2")
    private BrandService brandService;

    /**
     * 查询品牌的列表功能
     * @param page 当前页，int 默认为 1
     * @param rows 每页大小，int 默认为 5
     * @param key 搜索关键词，String
     * @param sortBy 排序字段，String 默认可以不传
     * @param desc 是否为降序，boolean 默认false为升序asc
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<BrandDTO>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", defaultValue = "false")Boolean desc
    ){
        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows, key, sortBy, desc));
    }

    /**
     * 新增商品
     * 实现事务管理
     * @param name   商品名称
     * @param image   图片url
     * @param letter  首字母
     * @param cids  属于的品类的id
     * @return
     */
    /**
    @PostMapping
    public ResponseEntity<Void> addBrandPage(@RequestParam(value = "name",required = true) String name,
                                             @RequestParam(value = "image",required = false) String image,
                                             @RequestParam(value = "letter",required = true)String letter,
                                             @RequestParam("cids") List<Long> cids){

        brandService.addBrand(name,image,letter, cids);
        *//**
         * 没有返回体 直接用build
         *//*
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
*/

    /**
     * 新增品牌
     * @param brandDTO 品牌对象
     * @param cids 商品分类的id的集合
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(BrandDTO brandDTO, @RequestParam("cids") List<Long> cids){
        brandService.addBrand(brandDTO, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类id查询品牌
     * @param id 分类id
     * @return
     */
    @GetMapping("/of/category")
    public ResponseEntity<List<BrandDTO>> queryBrandByCid(@RequestParam("id") Long id) {
        return ResponseEntity.ok(brandService.queryBrandByCid(id));
    }


    /**
     * 根据id查询品牌
     * @param bid
     * @return
     */
    @GetMapping("/{bid}")
    BrandDTO queryBrandById(@PathVariable("bid") Long bid) {
        return brandService.queryByBid(bid);
    }
    }
