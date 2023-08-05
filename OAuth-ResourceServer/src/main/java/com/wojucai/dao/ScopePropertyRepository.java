package com.wojucai.dao;

import com.wojucai.entity.po.ScopeProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScopePropertyRepository extends JpaRepository<ScopeProperty, Integer> {
    /**
     * 通过classId查询
     * @param classId
     * @return
     */
    List<ScopeProperty> findByClassId(Integer classId);
}
