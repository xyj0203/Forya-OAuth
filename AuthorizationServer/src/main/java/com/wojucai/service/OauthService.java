package com.wojucai.service;

import com.wojucai.Result;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.util.List;

/**
 * OAuth认证服务接口
 */
public interface OauthService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    Result userLogin(String username, String password, HttpServletRequest request) ;

    /**
     * 检查客户端和 密钥、Scope是否符合
     * @return
     */
    boolean checkClient(String clientId, String clientSecret);

    List<Integer> hasApproveList(String clientId, int parseInt);
}
