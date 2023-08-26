package com.wojucai.bean.metadata;

import feign.Param;
import feign.RequestLine;

import java.util.Map;

/**
 * 服务器元信息
 */
public interface OAuthService {

    /**
     * 获取服务器元信息
     * @return 返回元信息
     */
    @RequestLine("GET /.well-known/oauth-authorization-server")
    ServerMetadata getServerMetadata();

    @RequestLine("GET /.well-known/jwks.json")
    Map<Object,Object> getJwks();
}
