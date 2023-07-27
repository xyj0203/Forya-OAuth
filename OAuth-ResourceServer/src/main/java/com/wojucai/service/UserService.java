package com.wojucai.service;

import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.ClientVo;

import java.util.List;

/**
 * 用户业务接口类
 */
public interface UserService {

    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Integer> ids);

    /**
     * 通过用户名查询用户信息
     */
    List<User> queryUserByName();

    /**
     * 通过id查询
     * @param userQuery 用户查询参数的疯转
     * @return
     */
    ClientVo queryById(UserQuery userQuery);
}
