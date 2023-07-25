package com.wojucai.dao;

import com.wojucai.entity.Client;
import com.wojucai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: 客户端操作
 * @author: xuyujie
 * @date: 2023/06/11
 **/
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * 删除
     * @param id 客户端id
     * @return 返回是否删除成功
     */
    int deleteByClientId(Integer id);


}
