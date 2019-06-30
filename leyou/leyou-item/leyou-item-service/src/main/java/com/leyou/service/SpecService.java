package com.leyou.service;

import com.leyou.item.pojo.dto.SpecGroupDTO;
import com.leyou.item.pojo.dto.SpecParamDTO;

import java.util.List;

public interface SpecService {

    List<SpecGroupDTO> queryGroupByCid(Long cid);

    List<SpecParamDTO> querySpecParams(Long gid,Long cid,Boolean searching);

}
