package com.wojucai.dao;

import com.wojucai.entity.po.Role;
import com.wojucai.entity.po.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/05
 **/
@Repository
public interface ScopeRepository extends JpaRepository<Scope, Integer> {
}
