package com.wojucai.entity.vo;

import com.wojucai.entity.po.Role;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @description:User的视图对象
 * @author: xuyujie
 * @date: 2023/07/27
 **/
public class UserVo {
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
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 描述
     */
    private String description;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 角色
     */
    private Role role;
}
