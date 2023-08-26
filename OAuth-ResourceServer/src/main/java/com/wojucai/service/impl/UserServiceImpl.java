package com.wojucai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wojucai.Result;
import com.wojucai.configuration.context.UserContext;
import com.wojucai.dao.ConsentRepository;
import com.wojucai.dao.ScopePropertyRepository;
import com.wojucai.dao.UserRepository;
import com.wojucai.entity.po.*;
import com.wojucai.entity.reqParam.UserQuery;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.service.UserService;
import com.wojucai.util.TimeUtil;
import com.wojucai.util.converter.UserConverter;
import com.wojucai.util.invoker.MethodInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wojucai.entity.codeEnum.CacheConstant.SCOPE_CACHE;

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
    @Resource
    private ConsentRepository consentRepository;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ScopePropertyRepository scopePropertyRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        List<User> allById = userRepository.findAllById(ids);
        userRepository.deleteAllById(ids);
        allById.forEach(
                user -> {
                    consentRepository.deleteByUserId(user.getUserId());
                }
        );
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
        User user1 = userRepository.findById(user.getUserId()).get();
        BeanUtil.copyProperties(user,user1, CopyOptions.create().ignoreNullValue());
        return userRepository.save(user1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        consentRepository.deleteByUserId(id);
    }

    @Override
    public Page<UserVo> queryAll(UserQuery userQuery) {
        return query(userQuery, null, null, userConverter, userRepository);
    }

    @Override
    public UserVo queryUserInfo(UserQuery userQuery) {
        User user = new User();
        user.setUsername(userQuery.getUsername());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withMatcher("username", matcher1 -> matcher1.exact());
        return queryForOne(user, matcher, userConverter, userRepository);
    }

    @Override
    public User userLogin(String username) {
        User user = new User();
        user.setUsername(username);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                .withMatcher("username", matcher1 -> matcher1.exact());
        return queryForOne(user, matcher, userRepository);
    }

    @Override
    public Role userRole(Integer id) {
        return null;
    }

    @Override
    public UserVo userInfo() {
        Authorization authorization = UserContext.getAuthorizationCode();
        Assert.notNull(authorization, "authorizationCode can not be null");
        User user = userRepository.findById(authorization.getUserId()).orElse(null);
        if (user == null) {
            return new UserVo();
        }
        return userConverter.apply(user);
    }

    @Override
    public UserVo updateInfo(User user) {
        Authorization authorization = UserContext.getAuthorizationCode();
        List<Integer> range = redisTemplate.opsForList().range(SCOPE_CACHE + "User", 0, -1);
        // 求交集
        range.retainAll(new ArrayList<>(authorization.getScope()));
        // 查询出来的属性信息
        List<ScopeProperty> scopeProperties = scopePropertyRepository.findAllById(range);
        User copy = userRepository.findById(user.getUserId()).get();
        // 公共属性
        scopeProperties.forEach(
                 scopeProperty -> {
                     Object value;
                     if (scopeProperty.getBehavior() == 1 &&
                             // 获取user中不为空的
                             (value = MethodInvoker.invokeGetMethod(user,scopeProperty.getProperty()))!=null) {
                         // 获取属性
                         MethodInvoker.invokeSetMethod(copy,
                                 scopeProperty.getProperty(),value
                                );
                     }
                 });
        return userConverter.apply(userRepository.save(copy));
    }
}
