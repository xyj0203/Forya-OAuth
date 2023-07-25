package com.wojucai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:角色类
 * @author: xuyujie
 * @date: 2023/05/28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_role")
public class Role {
    /**
     * 角色Id
     */
    private Integer roleId;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色名称
     */
    private String roleName;
}
