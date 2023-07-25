package com.wojucai.service;

import com.wojucai.entity.Result;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;

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
    Result userLogin(String username, String password, HttpServletRequest request) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException;
}
