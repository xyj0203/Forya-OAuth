package com.wojucai.service.impl;

import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.service.ClientService;
import com.wojucai.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/04
 **/
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void testQueryById() {
        UserQuery userQuery = new UserQuery(null, null, null, null);
        userQuery.setId(1L);
        UserVo userVo = userService.queryById(userQuery);
        System.out.println(userVo);
    }

    @Test
    void testQueryByUserName() {
        UserQuery userQuery = new UserQuery(1, 10, null, null);
        userQuery.setUsername("admin");
        Page<UserVo> userVos = userService.queryByUsername(userQuery);
        System.out.println(userVos.getContent());
    }

    @Test
    void testQueryByAll() {
        UserQuery userQuery = new UserQuery(1, 10, null, null);
        Page<UserVo> userVos = userService.queryByUsername(userQuery);
        System.out.println(userVos.getContent());
    }
}
