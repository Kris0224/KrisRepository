package com.kris.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {


    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;
    /**
     *  性别
     */
    private String sex;

    /**
     *  年龄
     */
    private Integer age;

}
