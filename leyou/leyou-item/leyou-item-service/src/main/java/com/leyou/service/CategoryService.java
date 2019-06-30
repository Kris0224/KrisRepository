package com.leyou.service;

import com.leyou.entity.Category;
import com.leyou.item.pojo.dto.CategoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> queryCategoryById(long pid);

    List<CategoryDTO> queryCategoryByCIds(List<Long> cids);
}
