package com.leyou.service.impl;

import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LeyouException;
import com.leyou.common.utils.BeanHelper;
import com.leyou.entity.SpecGroup;
import com.leyou.entity.SpecParam;
import com.leyou.item.pojo.dto.SpecGroupDTO;
import com.leyou.item.pojo.dto.SpecParamDTO;
import com.leyou.mapper.SpecGroupMapper;
import com.leyou.mapper.SpecParamMapper;
import com.leyou.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SpecServiceImpl implements SpecService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    /**
     * 根据分类id cid查询规格组
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroupDTO> queryGroupByCid(Long cid) {
        // 根据分类查询规格组
        //创建规则对象
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list = groupMapper.select(group);
        // 健壮性判断
        if (CollectionUtils.isEmpty(list)) {
            throw new LeyouException(ExceptionEnums.SPEC_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(list, SpecGroupDTO.class);
    }

    /**
     * 根据规则组id gid 查询规格详情
     * @param gid
     * @return
     */
    @Override
    public List<SpecParamDTO> querySpecParams(Long gid,Long cid,Boolean searching) {
        // 健壮性 若参数gid 和 cid 和 searching字段都没有 抛异常
        if (gid == null && cid == null && searching == null) {
            throw new LeyouException(ExceptionEnums.INVALID_PARAM_ERROR);
        }

        //创建规格详情对象
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        // 健壮性判断
        if (CollectionUtils.isEmpty(list)) {
            throw new LeyouException(ExceptionEnums.SPEC_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(list, SpecParamDTO.class);
    }
    }

