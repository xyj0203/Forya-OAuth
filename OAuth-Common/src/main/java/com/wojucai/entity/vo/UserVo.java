package com.wojucai.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wojucai.entity.po.Role;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
     * 昵称
     */
    private String nickName;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 角色
     */
    private Role role;
}
