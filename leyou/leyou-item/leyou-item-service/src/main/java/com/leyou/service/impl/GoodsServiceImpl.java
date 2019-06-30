package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LeyouException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.common.vo.PageResult;
import com.leyou.entity.Sku;
import com.leyou.entity.Spu;
import com.leyou.entity.SpuDetail;
import com.leyou.item.pojo.dto.*;
import com.leyou.mapper.SkuMapper;
import com.leyou.mapper.SpuDetailMapper;
import com.leyou.mapper.SpuMapper;
import com.leyou.service.BrandService;
import com.leyou.service.CategoryService;
import com.leyou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

public class GoodsServiceImpl implements GoodsService {



    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper detailMapper;


    /**
     * 查询商品的spu
     * @param key  查询关键字
     * @param page 当前的页数
     * @param rows 每页的条数
     * @param saleable 上架还是下架商品
     * @return
     *
     * @sql
     * select * from tb_spu
     * where name like '% key %' and saleable = 1
     * order by update-time desc
     * limit (page-1)*rows,rows
     */
    @Override
    public PageResult<SpuDTO> querySpuByPage(String key, Integer page, Integer rows, Boolean saleable) {

        // 1.分页
        PageHelper.startPage(page, rows);

        // 2.条件过滤
        Example example = new Example(Spu.class);
        //创建条件
        Example.Criteria criteria = example.createCriteria();

        // 2.1.关键字过滤
        // and key like "% key %"
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%");
        }
        // 2.2.上下架过滤
        // AND saleable = saleable
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        // 3.排序
        // order by update_time desc
        example.setOrderByClause("update_time DESC");

        // 4.搜索
        // 获取查询结果 spu的集合
        List<Spu> list = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LeyouException(ExceptionEnums.GOODS_NOT_FOUND);
        }

        // 5.解析数据
        // 5.1.分页数据
        // 对查询到的spu集合进行分页
        PageInfo<Spu> info = new PageInfo<>(list);

        //获得总页数
        long totalCount = info.getTotal();

        //获得总页数
        int totalPage = info.getPages();
        //实体类类型转换
        List<SpuDTO> spuDTOList = BeanHelper.copyWithCollection(list, SpuDTO.class);

        /**
         *处理查询到的返回值spudtolist
         * 给分类和品牌字段赋值
         */

        handleCategoryAndBrandName(spuDTOList);

        //返回结果
        return new PageResult<>(totalCount, totalPage, spuDTOList);
    }



    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品的分类category 1/2/3的格式以及商品的品牌brand
     * @param spuDTOList
     */
    private void handleCategoryAndBrandName(List<SpuDTO> spuDTOList) {

        // 把查询到的分类的集合遍历拼接成 1/2/3的样式返回
        // 遍历查询到的spudto对象的集合
        for (SpuDTO spuDTO : spuDTOList) {
            // 处理分类名称
            List<Long> idList = spuDTO.getCategoryIds();
            // 根据id批量查询分类
            /*List<CategoryDTO> list = categoryService.queryCategoryByIdList(idList);
            StringBuilder sb = new StringBuilder();
            for (CategoryDTO c : list) {
                sb.append(c.getName()).append("/");
            }
            sb.deleteCharAt(sb.length() - 1);*/
            String name = categoryService.queryCategoryByCIds(idList)
                    .stream()
                    .map(CategoryDTO::getName)
                    .collect(Collectors.joining("/"));

            //给spu的categoryname字段赋值
            spuDTO.setCategoryName(name);
            // 处理品牌名称  获取spudto的品牌id bid
            Long brandId = spuDTO.getBrandId();
            //根据品牌bid查询branddto数据
            BrandDTO brandDTO = brandService.queryByBid(brandId);
            //获取brandto的name值且给spudto的brandname字段赋值
            spuDTO.setBrandName(brandDTO.getName());
        }
    }


    @Autowired
    private SkuMapper skuMapper;


    /**
     * 新增商品
     * @param spuDTO
     */
    @Override
    public void addGoods(SpuDTO spuDTO) {
    // 新增SPU 把spudto转成spu对象
        Spu spu = BeanHelper.copyProperties(spuDTO, Spu.class);

        // 默认下架 状态
        spu.setSaleable(false);
        int count = spuMapper.insertSelective(spu);
        if (count != 1) {
            throw new LeyouException(ExceptionEnums.INSERT_OPERATION_FAIL);
        }


        // 新增sku 给spu对象中的sku属性赋值
        insertSkuList(spuDTO.getSkus(), spu.getId());
    }

    /**
     * 给spu对象中的sku属性赋值
     * @param skuDTOList
     * @param spuId
     */
    private void insertSkuList(List<SkuDTO> skuDTOList, Long spuId) {
        int count;// 新增Sku
        //把页面传来的dto对象转化为sku对象集合
        List<Sku> skuList = BeanHelper.copyWithCollection(skuDTOList, Sku.class);
        //遍历sku对象
        for (Sku sku : skuList) {
            //把spu的id赋值给sku表中的id字段
            sku.setSpuId(spuId);
            // 设置上下架状态为下架状态
            sku.setEnable(false);
        }
        //skulist参数传入 新增sku
        count = skuMapper.insertList(skuList);
        if(count != skuList.size()){
            throw new LeyouException(ExceptionEnums.INSERT_OPERATION_FAIL);
        }
    }

    /**
     * 商品的上下架
     * @param id
     * @param saleable
     */
    @Transactional
    public void updateSaleable(Long id, Boolean saleable) {
        // 1.更新spu上下架
        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(saleable);
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new LeyouException(ExceptionEnums.UPDATE_OPERATION_FAIL);
        }
        // 2.更新sku的上下架
        // 2.1.设置更新字段
        Sku sku = new Sku();
        sku.setEnable(saleable);
        // 2.2.更新的匹配条件
        Example example = new Example(Sku.class);
        //相当于
        // and id = spuId
        example.createCriteria().andEqualTo("spuId", id);
        //执行新增操作
        count = skuMapper.updateByExampleSelective(sku, example);
        if(count <= 0){
            throw new LeyouException(ExceptionEnums.UPDATE_OPERATION_FAIL);
        }
    }

    /**
     * 根据spuid查询spudetail
     * @param spuId
     * @return
     */
    public SpuDetailDTO querySpuDetailBySpuId(Long spuId) {
        //根据主键查询
        SpuDetail spuDetail = detailMapper.selectByPrimaryKey(spuId);
        //健壮性判断
        if (spuDetail == null) {
            throw new LeyouException(ExceptionEnums.GOODS_NOT_FOUND);
        }
        //类型转换
        return BeanHelper.copyProperties(spuDetail, SpuDetailDTO.class);
    }

    /**
     * 根据spuid查询spu
     * @param spuId
     * @return
     */
    public List<SkuDTO> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> list = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(list)) {
            throw new LeyouException(ExceptionEnums.GOODS_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(list, SkuDTO.class);
    }

    /**
     * 更新商品
     * 先删除，在写入的方案
     * @param spuDTO
     */
    public void updateGoods(SpuDTO spuDTO){

       // 0.获取spu的id
        Long spuId = spuDTO.getId();
        if (spuId == null) {
            throw new LeyouException(ExceptionEnums.INVALID_PARAM_ERROR);
        }
        // 1.删除SKU
        // delete from sku_table where id = spuid
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        // 1.1.查询以前有几个SKU
        int size = skuMapper.selectCount(sku);
        if(size > 0) {
            // 1.2.删除
            int count = skuMapper.delete(sku);
            if (count != size) {
                throw new LeyouException(ExceptionEnums.UPDATE_OPERATION_FAIL);
            }
        }

        // 2.更新SPU
        //类型转换
        Spu spu = BeanHelper.copyProperties(spuDTO, Spu.class);

        //三个dto不需要的字段 赋值为null
        spu.setSaleable(null);
        spu.setUpdateTime(null);
        spu.setCreateTime(null);

        //根据主键更新
        //update tb_spu set xxxxx where id = spuid
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count != 1) {
            throw new LeyouException(ExceptionEnums.UPDATE_OPERATION_FAIL);
        }

        // 3.更新SpuDetail  因为spu和spudetail是同一张表的垂直分割
        SpuDetail spuDetail = BeanHelper.copyProperties(spuDTO.getSpuDetail(), SpuDetail.class);
        count = detailMapper.updateByPrimaryKey(spuDetail);
        if (count != 1) {
            throw new LeyouException(ExceptionEnums.UPDATE_OPERATION_FAIL);
        }


        // 4.新增SKU
        //调用insertskulist方法 给sku实体类中的sku属性赋值
        insertSkuList(spuDTO.getSkus(), spu.getId());
    }

}
