package com.wojucai.service;

import com.wojucai.Result;
import com.wojucai.entity.po.Role;
import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.entity.vo.UserVo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 用户业务接口类
 */
public interface UserService {


    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Long> ids);

    /**
     * 通过id查询
     * @param userQuery 用户查询参数的封装
     * @return
     */
    UserVo queryById(UserQuery userQuery);

    /**
     * 通过用户名查询
     * @param userQuery
     * @return
     */
    Page<UserVo> queryByUsername(UserQuery userQuery);

    /**
     * 添加用户
     * @param user 用户实体对象
     * @return
     */
    User insertUser(User user);

    /**
     * 更新用户信息
     * @param user 用户实体对象
     * @return
     */
    User updateUser(User user);

    /**
     * 通过id进行删除
     * @param id 用户id
     * @return
     */
    void deleteById(Long id);

    /**
     * 分页查询
     * @param userQuery 客户端查询
     * @return
     */
    Page<UserVo> queryAll(UserQuery userQuery);

    /**
     * 查询用户信息
     * @param userQuery
     * @return
     */
    UserVo queryUserInfo(UserQuery userQuery);

    /**
     * 用户登录
     * @param username
     * @return
     */
    User userLogin(String username);

    /**
     * 查询Id对应的Role
     * @param id
     * @return
     */
    Role userRole(Integer id);

    /**
     * 获取用户信息
     * @return
     */
    UserVo userInfo();

    /**
     * 用户自己更新信息
     * @param user
     * @return
     */
    UserVo updateInfo(User user);
}
