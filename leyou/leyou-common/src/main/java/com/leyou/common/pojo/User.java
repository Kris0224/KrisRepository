package com.leyou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试自定义异常的实体类
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    /**
     *  用户名
     */
    private String username;

    /**
     *  用户编号
     */
    private Integer id;

}
