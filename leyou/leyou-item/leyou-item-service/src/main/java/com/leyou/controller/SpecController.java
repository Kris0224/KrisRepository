package com.leyou.controller;


import com.leyou.item.pojo.dto.SpecGroupDTO;
import com.leyou.item.pojo.dto.SpecParamDTO;
import com.leyou.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 */
@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    /**
     * 根据分类查询规格组
     * @param cid 分类id
     * @return 规格组集合
     */
    @GetMapping("groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroupByCid(@RequestParam("id") Long cid) {
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }

/*    *//**
     * 根据组id查询组内参数
     * @param gid 组id
     * @return
     *//*
    @GetMapping("params")
    public ResponseEntity<List<SpecParamDTO>> queryParamsByGid(@RequestParam("gid") Long gid){
        return ResponseEntity.ok(specService.queryParamsByGid(gid));
    }*/


    /**
     * 根据条件规格参数
     * @param gid 组id
     * @param cid 分类id
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParamDTO>> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching
    ){
        return ResponseEntity.ok(specService.querySpecParams(gid, cid, searching));
    }
}
