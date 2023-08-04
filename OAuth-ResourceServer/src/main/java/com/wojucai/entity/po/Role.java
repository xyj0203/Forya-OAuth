package com.wojucai.entity.po;

import com.wojucai.entity.validate.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 角色类
 * @author: xuyujie
 * @date: 2023/07/27
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_role")
public class Role {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空", groups = {Update.class})
    @javax.persistence.Id
    @GeneratedValue
    private Integer roleId;

    /**
     * 角色描述
     */
    @NotBlank(message = "角色描述不能为空")
    private String roleDesc;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    private String roleName;
}
