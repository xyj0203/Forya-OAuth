package com.wojucai.service.impl;

import com.wojucai.dao.UserRepository;
import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:用户业务实体类
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public void batchDelete(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    @Override
    public UserVo queryById(Long id) {
        return null;
    }

    @Override
    public Page<UserVo> queryByUsername(String username) {
        return null;
    }

    @Override
    public User insertUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }
}
