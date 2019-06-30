package com.leyou.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 和数据库一一对应的实体类
 */
//@Table(name="tb_category")
@Data
public class Category {
//	@Id
//	@KeySql(useGeneratedKeys=true)
	private Long id;
	private String name;
	private Long parentId;
	private Boolean isParent;
	private Integer sort;
	private Date createTime;
	private Date updateTime;
}