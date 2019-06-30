package com.leyou.controller;



import com.leyou.entity.Category;
import com.leyou.item.pojo.dto.CategoryDTO;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 查询商品类目的controller
 * 根据父节点查询商品类目
 */
@RestController
@RequestMapping("category")  //一级路径
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/of/parent") //二级路径
    public ResponseEntity<List<CategoryDTO>> queryCategoryById(@RequestParam("pid") long pid){

        /**
         * 获取返回类别的list集合
         */
        List<CategoryDTO> resultlist = categoryService.queryCategoryById(pid);

        /**
         * 数据转换 category实体类 转换成 catedto实体类
         * 防止数据库表的结构泄漏
         */

        /**
         * 返回200成功状态码
         * 返回类别的list集合到响应体中
         */
        return ResponseEntity.ok().body(resultlist);

    }

    /**
     * 根据cid批量查询分类
     * @param cids
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<CategoryDTO>> queryCategoryByIds(@RequestParam("ids") List<Long> cids){
        List<CategoryDTO> list = categoryService.queryCategoryByCIds(cids);
        return ResponseEntity.ok(list);
    }


}
