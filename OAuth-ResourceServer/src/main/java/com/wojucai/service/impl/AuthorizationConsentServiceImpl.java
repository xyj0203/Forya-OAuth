package com.wojucai.service.impl;

import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    /**
     * 保存授权关系
     * @param authorizationConsent 关系实体
     */
    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {

    }

    /**
     * 移除授权关系
     * @param authorizationConsent 关系实体
     */
    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {

    }

    /**
     * 查询授权关系
     * @param registeredClientId 注册的客户端名称
     * @param principalName 用户名
     * @return
     */
    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return null;
    }
}
