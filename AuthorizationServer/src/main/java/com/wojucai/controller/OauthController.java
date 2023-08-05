package com.wojucai.controller;

import com.wojucai.entity.Result;
import com.wojucai.entity.ServerMetadata;
import com.wojucai.entity.codeEnum.ResultEnum;
import com.wojucai.entity.req.LoginReq;
import com.wojucai.entity.req.OAuthReq;
import com.wojucai.service.OauthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;

/**
 * @description:Oauth认证
 * @author: xuyujie
 * @date: 2023/05/26
 **/
@Controller
@Api(tags = "Controller-Oauth")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @GetMapping("/oauth/2.0/authorize")
    public String authorize(OAuthReq oAuthReq) {
        if (oAuthReq.getClient_id() == null) {
            return null;
        }
        return null;
    }

    @PostMapping("/userLogin")
    @ResponseBody
    public Result userLogin(@RequestBody LoginReq loginReq, HttpServletRequest request) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String username = loginReq.getUsername();
        String password = loginReq.getPassword();
        if (username == null || !(username.length() >= 5 && username.length() <= 11)) {
            return  Result.fail(ResultEnum.ParamsIllegal);
        }
        if (password == null || !password.matches("^\\w{5,11}$")) {
            return  Result.fail(ResultEnum.ParamsIllegal);
        }
        return oauthService.userLogin(username, password,request);
    }

    @PostMapping("/demo")
    @ResponseBody
    public Result test() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return new Result();
    }

    @GetMapping("userLogout")
    @ResponseBody
    public Result userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userLogin", false);
        session.setAttribute("loginState", null);
        return Result.success(ResultEnum.UserLogout);
    }

    @GetMapping("/well-known/oauth-authorization-server")
    @ResponseBody
    public ServerMetadata oauthAuthorizationServer() {
        ServerMetadata serverMetadata = new ServerMetadata();
        return serverMetadata;
    }
}
