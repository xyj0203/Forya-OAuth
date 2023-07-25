package com.wojucai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wojucai.entity.OnlineState;
import com.wojucai.entity.Result;
import com.wojucai.entity.Role;
import com.wojucai.entity.User;
import com.wojucai.entity.codeEnum.ResultEnum;
import com.wojucai.mapper.RoleMapper;
import com.wojucai.mapper.UserMapper;
import com.wojucai.service.OauthService;
import com.wojucai.utils.encrypt.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;

/**
 * @description:OAuth认证服务接口
 * @author: xuyujie
 * @date: 2023/05/27
 **/
@Service
public class OauthServiceImpl implements OauthService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private EncryptUtil encryptUtil;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public Result userLogin(String username, String password, HttpServletRequest request) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        HttpSession session = request.getSession();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(
                User::getUsername, username
        ));
        if (user == null) {
            return Result.fail(ResultEnum.UserNotExist);
        }
        if (!encryptUtil.decode(password, user.getPassword())) {
            //登录失败
            session.setAttribute("userLogin", false);
            return Result.fail(ResultEnum.UserIllegal);
        }
        String roleName = roleMapper.
                selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleId, user.getRole()))
                .getRoleName();
        session.setAttribute("userLogin", true);
        OnlineState onlineState = new OnlineState(roleName, user.getUserId());
        session.setAttribute("loginState", onlineState);
        return Result.success( onlineState,ResultEnum.LoginSuccess);
    }
}
