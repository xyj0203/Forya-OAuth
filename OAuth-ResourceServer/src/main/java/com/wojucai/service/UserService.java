package com.wojucai.service;

import com.wojucai.entity.User;

import java.util.List;

/**
 * 用户业务接口类
 */
public interface UserService {

    /**
     * 通过用户名查询用户信息
     */
    List<User> queryUserByName();
}
