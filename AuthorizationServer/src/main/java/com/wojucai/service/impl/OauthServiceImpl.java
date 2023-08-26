package com.wojucai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wojucai.Result;
import com.wojucai.entity.OnlineState;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.User;
import com.wojucai.enums.ResultEnum;
import com.wojucai.service.BaseService;
import com.wojucai.service.ClientService;
import com.wojucai.service.OauthService;
import com.wojucai.service.UserService;
import com.wojucai.utils.encrypt.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @description:OAuth认证服务接口
 * @author: xuyujie
 * @date: 2023/05/27
 **/
@Service
public class OauthServiceImpl implements OauthService {

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EncryptUtil encryptUtil;
    @Override
    public Result userLogin(String username, String password, HttpServletRequest request) {
        if (username == null || !(username.length() >= 5 && username.length() <= 11)) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        if (password == null || !password.matches("^\\w{5,11}$")) {
            return Result.fail(ResultEnum.ParamsIllegal);
        }
        Result result = userService.userLogin(username);
        if (result.getObject() == null) {
            return Result.fail(ResultEnum.UserNotExist);
        }
        User user = BeanUtil.mapToBean((Map<?, ?>) result.getObject(),User.class,false,null);

        // 登录成功
        if (encryptUtil.decode(password, user.getPassword())) {
            // 角色名
            OnlineState onlineState = new OnlineState(user.getRole(), user.getUserId(),true);
            HttpSession session = request.getSession();
            session.setAttribute("OnlineState", onlineState);
            return Result.success(onlineState,ResultEnum.LoginSuccess);
        }
        return Result.fail(ResultEnum.UserIllegal);
    }

    @Override
    public boolean checkClient(String clientId, String clientSecret) {
        // 找到client
        Result result = clientService.queryClientById(clientId);
        if (result.getObject() == null) {
            return false;
        }
        //
        Client client = BeanUtil.mapToBean((Map<?, ?>) result.getObject(),Client.class,false,null);
        System.out.println(client);
        if (client.getClientId().equals(clientId) && client.getClientSecret().equals(clientSecret)) {
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> hasApproveList(String clientId, int parseInt) {
        return null;
    }
}
