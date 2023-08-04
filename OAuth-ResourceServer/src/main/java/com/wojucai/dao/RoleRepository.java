package com.wojucai.dao;

import com.wojucai.entity.po.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: 角色类
 * @author: xuyujie
 * @date: 2023/08/04
 **/
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
