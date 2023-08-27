package com.wojucai.controller.BaseController;

import com.nimbusds.jose.jwk.ECKey;
import com.wojucai.entity.KeyStore;
import com.wojucai.entity.ServerMetadata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:服务器元信息
 * @author: xuyujie
 * @date: 2023/08/13
 **/
@RestController
@RequestMapping("/.well-known")
@Api(tags = "Controller-WellKnown")
public class WellKnownController {
    private final ServerMetadata serverMetadata;
    private final KeyStore keyStore;

    public WellKnownController(ServerMetadata serverMetadata, KeyStore keyStore) {
        this.serverMetadata = serverMetadata;
        this.keyStore = keyStore;
    }

    @ApiOperation("获得授权服务器的元信息")
    @GetMapping("/oauth-authorization-server")
    public ServerMetadata getMetadata() {
        return serverMetadata;
    }

    /**
     * 获取 JWK 密钥
     *
     * @return JWK
     */
    @GetMapping("/jwks.json")
    @ApiOperation("获取JWK密钥")
    public Map<String, ?> getJwks() {
        ECKey publicJWK = keyStore.getEcKey().toPublicJWK();
        Map<String, Object> json = publicJWK.toJSONObject();
        json.put("alg", "ES256");
        HashMap<String, Object> ans = new HashMap<>();
        ans.put("keys", Collections.singleton(json));
        return ans;
    }
}
