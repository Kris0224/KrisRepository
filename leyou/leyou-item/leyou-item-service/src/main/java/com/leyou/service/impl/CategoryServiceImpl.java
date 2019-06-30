package com.leyou.service.impl;

import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LeyouException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.entity.Category;
import com.leyou.item.pojo.dto.CategoryDTO;
import com.leyou.mapper.CategoryMapper;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据分类id 查询分类的信息
     * @param pid
     * @return
     */
    @Override
    public List<CategoryDTO> queryCategoryById(long pid) {

        List<Category> list = categoryMapper.queryCategoryById(pid);

        /**
         * 判断集合是否为空
         * 若为空 抛出异常enum
         */
        if (CollectionUtils.isEmpty(list)){
            throw new LeyouException(ExceptionEnums.CATEGORY_NOT_FOUND);
        }

        /**
         * 使用工具类将category实体类的list集合转换成categorydto类型的list集合
         *
         * 返回
         */
        return BeanHelper.copyWithCollection(list, CategoryDTO.class);
    }

    /**
     * 根据cids 可变参数 类型的id 查询商品分类的list集合
     * @param cids
     * @return
     */
    @Override
    public List<CategoryDTO> queryCategoryByCIds(List<Long> cids){
        List<Category> list = categoryMapper.selectByIdList(cids);
       //健壮性判断
        if (CollectionUtils.isEmpty(list)) {
            // 没找到，返回404
            throw new LeyouException(ExceptionEnums.CATEGORY_NOT_FOUND);
        }
        //类型转换
        return BeanHelper.copyWithCollection(list, CategoryDTO.class);
    }

}
