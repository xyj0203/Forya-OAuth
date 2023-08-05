package com.wojucai.entity.vo;

import com.wojucai.entity.po.Role;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @description:User的视图对象
 * @author: xuyujie
 * @date: 2023/07/27
 **/
@Data
public class UserVo {
    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 性别
     */
    private Character sex;

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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 角色
     */
    private Role role;
}
