package com.wojucai.service.impl;

import com.wojucai.dao.UserRepository;
import com.wojucai.entity.po.User;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.service.UserService;
import com.wojucai.util.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @description:用户业务实体类
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@Service
public class UserServiceImpl extends AbstractImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    @Override
    public void batchDelete(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    @Override
    public UserVo queryById(UserQuery userQuery) {
        Optional<User> optionalClient = userRepository.findById(userQuery.getId());
        UserVo userVo = optionalClient.map(userConverter).orElse(null);
        return userVo;
    }

    @Override
    public Page<UserVo> queryByUsername(UserQuery userQuery) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("username", match -> match.contains());
        User user = new User();
        user.setUsername(userQuery.getUsername());
        return query(userQuery, user, matcher, userConverter, userRepository);
    }

    @Override
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserVo> queryAll(UserQuery userQuery) {
        return query(userQuery, null, null, userConverter, userRepository);
    }
}
