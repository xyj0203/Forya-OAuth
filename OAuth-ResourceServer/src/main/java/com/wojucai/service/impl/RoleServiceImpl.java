package com.wojucai.service.impl;

import com.wojucai.dao.RoleRepository;
import com.wojucai.entity.po.Role;
import com.wojucai.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description: 角色类
 * @author: xuyujie
 * @date: 2023/08/10
 **/
@Service
public class RoleServiceImpl extends AbstractImpl implements RoleService {

    @Resource
    private RoleRepository repository;

    @Override
    public Role queryById(Integer id) {
        Optional<Role> role = repository.findById(id);
        Role role1 = role.orElseGet(null);
        return role1;
    }

    @Override
    public List<Role> queryAll() {
        return repository.findAll();
    }
}
