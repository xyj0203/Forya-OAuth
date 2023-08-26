package com.wojucai.dao;

import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: 客户端操作
 * @author: xuyujie
 * @date: 2023/06/11
 **/
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Client findByClientId(String clientId);
}
