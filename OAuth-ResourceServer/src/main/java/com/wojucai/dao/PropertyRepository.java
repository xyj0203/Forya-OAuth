package com.wojucai.dao;

import com.wojucai.entity.po.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    /**
     * 通过classId查询
     * @param classId
     * @return
     */
    List<Property> findByClassId(Integer classId);
}
