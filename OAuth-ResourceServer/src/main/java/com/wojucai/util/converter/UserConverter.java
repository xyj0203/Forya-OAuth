package com.wojucai.util.converter;

import com.google.common.cache.Cache;
import com.wojucai.dao.RoleRepository;
import com.wojucai.entity.codeEnum.CacheConstant;
import com.wojucai.entity.codeEnum.ParamConstants;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.Role;
import com.wojucai.entity.po.User;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.entity.vo.UserVo;
import com.wojucai.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * @description: 转换类
 * @author: xuyujie
 * @date: 2023/08/04
 **/
@Component
public class UserConverter implements Function<User, UserVo> {

    @Resource
    private RoleRepository roleRepository;

    @Autowired
    @Qualifier(value = "roleCache")
    private Cache<Integer, Role> roleCache;

    @Override
    public UserVo apply(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        if (user.getSex() != null) {
            userVo.setSex(user.getSex() == 0 ? '男' : '女');
        }
        try {
            userVo.setRole(roleCache.get(user.getRole(),() ->
                roleRepository.findById(user.getRole()).get()
            ));
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (user.getBirthday() != null) {
            LocalDate birthDate = user.getBirthday(); // 获取生日的 LocalDate 对象
            LocalDateTime currentDateTime = LocalDateTime.now(); // 获取当前的 LocalDateTime 对象
            LocalDate currentDate = currentDateTime.toLocalDate(); // 获取当前日期的 LocalDate 对象
            Period period = Period.between(birthDate, currentDate); // 计算日期差异
            int age = period.getYears(); // 获取年龄
            userVo.setAge(age);
        } else {
            userVo.setAge(0);
        }
        if (userVo.getUserImage() == null) {
            userVo.setUserImage(ParamConstants.DEFAULT_IMG);
        }
        userVo.setCreateTime(null);
        userVo.setUpdateTime(null);
        return userVo;
    }
}
