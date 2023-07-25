package com.wojucai.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:用户实体类
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
public class User {
    /**
     * 用户Id
     */
    @Id
    @GeneratedValue
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 描述
     */
    private String description;
}
