package com.leyou.item.pojo.dto;

import lombok.Data;

/**
 *  Data Transfer Object 和前台数据交换的实体类
 */
@Data
public class CategoryDTO {
	private Long id;
	private String name;
	private Long parentId;
	private Boolean isParent;
	private Integer sort;
}