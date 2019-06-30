package com.leyou.item.pojo.dto;

import lombok.Data;

/**
 * 品牌的 data transfer object 实体类
 */
@Data
public class BrandDTO {
    private Long id;
    private String name;
    private String image;
    private Character letter;
}
