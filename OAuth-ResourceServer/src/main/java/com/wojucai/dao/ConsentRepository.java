package com.wojucai.dao;

import com.wojucai.entity.po.Consent;
import com.wojucai.entity.po.key.ConsentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @description: 客户端关联类
 * @author: xuyujie
 * @date: 2023/08/04
 **/
@Repository
public interface ConsentRepository extends JpaRepository<Consent, ConsentKey>, JpaSpecificationExecutor<Consent> {

    Consent findByClientIdAndAndUserId(String clientId, Long userId);

    Consent findByClientId(String clientId);

    void deleteByClientId(String clientId);

    void deleteByUserId(Long userId);
}
