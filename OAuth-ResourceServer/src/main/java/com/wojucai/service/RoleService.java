package com.wojucai.service;

import com.wojucai.entity.po.Role;

import java.util.List;

public interface RoleService {

    /**
     * 通过Id查询
     * @return
     */
    Role queryById(Integer id);

    /**
     * 查询所有角色
     * @return
     */
    List<Role> queryAll();
}
