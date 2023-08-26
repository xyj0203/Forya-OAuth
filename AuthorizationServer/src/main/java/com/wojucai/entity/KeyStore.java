package com.wojucai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/13
 **/
@Data
public class KeyStore {
    /**
     * JWT 签名私钥 (ECDSA)
     */
    private ECKey ecKey;

    /**
     * JWT 刷新密钥 (HMAC)
     */
    private byte[] refreshKey;

    /**
     * 获取登录令牌签名对象
     *
     * @return JWSSigner
     */
    @JsonIgnore
    public JWSSigner getAccessKeySigner() {
        try {
            return new ECDSASigner(ecKey);
        } catch (JOSEException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 获取刷新令牌签名对象
     *
     * @return JWSSigner
     */
    @JsonIgnore
    public JWSSigner getRefreshKeySigner() {
        try {
            return new MACSigner(refreshKey);
        } catch (KeyLengthException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 生成新的密钥信息
     *
     * @return 生成的密钥
     */
    public static KeyStore generate() {
        KeyStore keyStore = new KeyStore();
        ECKey jwk;
        try {
            jwk = new ECKeyGenerator(Curve.P_256)
                    .keyUse(KeyUse.SIGNATURE) // indicate the intended use of the key
                    .keyID(UUID.randomUUID().toString()) // give the key a unique ID
                    .generate();
        } catch (JOSEException e) {
            throw new IllegalStateException(e);
        }
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
        keyStore.setEcKey(jwk);
        keyStore.setRefreshKey(sharedSecret);
        return keyStore;
    }
}
