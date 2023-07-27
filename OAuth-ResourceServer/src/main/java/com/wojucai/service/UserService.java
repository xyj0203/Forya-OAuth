package com.wojucai.service;

import com.wojucai.Result;
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
     * @param userQuery 用户查询参数的疯转
     * @return
     */
    UserVo queryById(UserQuery userQuery);

    /**
     * 通过用户名查询
     * @param userQuery 用户参数封装
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
    int deleteById(Long id);
}
