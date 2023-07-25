package com.wojucai.bean.metadata;

import feign.Param;
import feign.RequestLine;

/**
 * 服务器元信息
 */
public interface OAuthService {

    /**
     * 获取服务器元信息
     * @return 返回元信息
     */
    @RequestLine("GET /well-known/oauth-authorization-server")
    ServerMetadata getServerMetadata();

    @RequestLine("GET /")
    TokenResponse getTokenResponse(@Param("token") String tokenEndpoint, String clientAuthorization);
}
