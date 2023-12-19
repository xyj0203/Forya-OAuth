package com.wojucai.entity.po;

import com.wojucai.entity.validate.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @description: 角色资源关联
 * @author: xuyujie
 * @date: 2023/12/18
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_resource")
public class RoleResource {

    @NotNull(message = "id不能为空", groups = {Update.class})
    @javax.persistence.Id
    @GeneratedValue
    private Integer id;

    private Integer roleId;

    private Integer resourceId;
}
