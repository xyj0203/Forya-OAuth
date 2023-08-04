package com.wojucai.util.converter;

import com.wojucai.dao.RoleRepository;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.User;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.entity.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Override
    public UserVo apply(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user,userVo);
        if (user.getSex() != null) {
            userVo.setSex(user.getSex() == 0 ? '男' : '女');
        }
        userVo.setRole(roleRepository.findById(user.getRole()).get());
        return userVo;
    }
}
