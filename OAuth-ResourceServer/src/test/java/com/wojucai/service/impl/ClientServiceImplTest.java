package com.wojucai.service.impl;

import com.wojucai.entity.po.Scope;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @description: 测试ClientServiceImpl
 * @author: xuyujie
 * @date: 2023/07/29
 **/
@SpringBootTest
public class ClientServiceImplTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void testFindAll() {
        Page<ClientVo> page = clientService.queryAll(new ClientQuery(1, 10, null, null));
        System.out.println(page.getContent());
    }

    @Test
    public void testFindByName() {
        ClientQuery clientQuery = new ClientQuery(1, 10, null, "createTime");
        clientQuery.setClientName("莴苣");
        Page<ClientVo> page = clientService.queryByClientName(clientQuery);
        System.out.println(page.getContent());
    }

}
