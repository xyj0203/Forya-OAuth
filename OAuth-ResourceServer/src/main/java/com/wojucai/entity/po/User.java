package com.wojucai.entity.po;

import com.wojucai.entity.annotation.ScopeAnnotations;
import com.wojucai.entity.annotation.PropertyAnnotations;
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
@ScopeAnnotations(classDescription = "个人信息")
public class User extends BaseEntity{

    /**
     * 用户Id
     */
    @Id
    @GeneratedValue
    private Long userId;

    /**
     * 用户名
     */
    @PropertyAnnotations(description = "用户名", behavior = 1)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    @PropertyAnnotations(description = "头像", behavior = 1)
    private String userImage;

    /**
     * 性别
     */
    @PropertyAnnotations(description = "性别", behavior = 1)
    private Integer sex;

    /**
     * 年龄
     */
    @PropertyAnnotations(description = "年龄", behavior = 1)
    private Integer age;

    /**
     * 描述
     */
    @PropertyAnnotations(description = "描述", behavior = 1)
    private String description;

    /**
     * 角色
     */
    @PropertyAnnotations(description = "角色", behavior = 0)
    private Integer role;
}
