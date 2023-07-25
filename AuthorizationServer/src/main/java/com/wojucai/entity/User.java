package com.wojucai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:用户实体类
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User {
    /**
     * 用户Id
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 角色
     */
    @TableField("role")
    private Integer role;
}
