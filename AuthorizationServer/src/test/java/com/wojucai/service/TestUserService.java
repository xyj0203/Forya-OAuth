package com.wojucai.service;

import com.wojucai.Result;
import com.wojucai.entity.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUserService {
    @Autowired
    private BaseService baseService;
    @Autowired
    private UserService userService;



    @Test
    void testInsertUser() {
        Result result = userService.insertUser(new User());
        System.out.println(result);
    }

    @Test
    void testQuery() {
        Result admin = userService.findByUsername("admin");
    }

//    @Test
//    void encodePassword() {
//        System.out.println(passwordEncoder.encode("123456"));
//    }

    @Test
    void testUserLogin() {
        Result admin = userService.userLogin("admin");
        System.out.println(admin);
    }
}
