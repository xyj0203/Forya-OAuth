package com.wojucai.service.impl;

import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:用户业务实体类
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void batchDelete(List<Integer> ids) {

    }

    @Override
    public List<User> queryUserByName() {
        return null;
    }

    @Override
    public UserVo queryById(UserQuery userQuery) {
        return null;
    }
}
