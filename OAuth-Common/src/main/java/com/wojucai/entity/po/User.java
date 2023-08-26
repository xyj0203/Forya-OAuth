package com.wojucai.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wojucai.entity.annotation.ScopeAnnotations;
import com.wojucai.entity.annotation.PropertyAnnotations;
import com.wojucai.entity.validate.Default;
import com.wojucai.entity.validate.Update;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

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
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {Default.class})
    @PropertyAnnotations(description = "用户名")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,15}$", message = "用户名长度在6-15位", groups = {Default.class})
    private String username;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空", groups = {Default.class})
    @PropertyAnnotations(description = "昵称", behavior = 1)
    private String nickName;

    /**
     * 生日
     */
    @PropertyAnnotations(description = "生日", behavior = 1)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message = "密码不能位空", groups = {Default.class})
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
     * 描述
     */
    @NotBlank(message = "描述不能为空", groups = {Default.class})
    @PropertyAnnotations(description = "描述", behavior = 1)
    private String description;

    /**
     * 角色
     */
    @NotNull(message = "角色不能为空", groups = {Default.class})
    @PropertyAnnotations(description = "角色")
    private Integer role;
}
