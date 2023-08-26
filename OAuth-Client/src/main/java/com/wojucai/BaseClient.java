package com.wojucai;

import com.nimbusds.jose.util.Base64;
import com.wojucai.json.GsonDecoder;
import com.wojucai.json.GsonEncoder;

/**
 * @description: 客户端
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public abstract class BaseClient {

    /**
     * 解码器
     */
    protected final GsonDecoder gsonDecoder = new GsonDecoder();

    /**
     * 编码器
     */
    protected final GsonEncoder gsonEncoder = new GsonEncoder();

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
