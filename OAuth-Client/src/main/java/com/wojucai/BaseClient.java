package com.wojucai;

import com.nimbusds.jose.util.Base64;
import com.wojucai.json.GsonDecoder;

/**
 * @description: 客户端
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public abstract class BaseClient {

    protected final GsonDecoder gsonDecoder = new GsonDecoder();

    /**
     * 获取认证
     * @param userName clientId
     * @param password clientSecret
     * @return
     */
    protected static String basicAuth(String userName, String password) {
        return "Basic " + Base64.encode((userName + ":" + password).getBytes());
    }
}
