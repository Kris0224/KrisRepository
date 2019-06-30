package com.leyou.mapper;

import com.leyou.common.mapper.MyMapper;
import com.leyou.entity.Category;
import com.leyou.item.pojo.dto.CategoryDTO;
import java.util.List;

public  interface CategoryMapper extends MyMapper<Category> {
    /**
     * 根据父类的id查询类目信息的list集合
     * @param pid
     * @return
     */
     List<Category> queryCategoryById(long pid);

    /**
     * 根据多个cid查询对应的分类集合
     * @param cids
     * @return
     */
     List<CategoryDTO> selectByCids(List<Long> cids);
}
