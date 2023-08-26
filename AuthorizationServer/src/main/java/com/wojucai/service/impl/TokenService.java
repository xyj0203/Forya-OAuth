package com.wojucai.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.wojucai.entity.AccessTokenResponse;
import com.wojucai.entity.po.AuthorizationCode;
import com.wojucai.entity.KeyStore;
import com.wojucai.entity.ServerMetadata;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/13
 **/
@Service
public class TokenService {

    private final ServerMetadata serverMetadata;
    private final JWSSigner accessKeySigner;
    private final JWSSigner refreshKeySigner;
    private final JWSHeader accessJwsHeader;
    private final JWSHeader refreshJwsHeader;
    private final MACVerifier refreshTokenVerifier;

    public TokenService(KeyStore keyStore, ServerMetadata serverMetadata) {
        this.serverMetadata = serverMetadata;
        this.accessKeySigner = keyStore.getAccessKeySigner();
        this.refreshKeySigner = keyStore.getRefreshKeySigner();
        this.accessJwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256)
                .type(JOSEObjectType.JWT)
                .keyID(keyStore.getEcKey().getKeyID())
                .build();
        try {
            this.refreshTokenVerifier = new MACVerifier(keyStore.getRefreshKey());
        } catch (JOSEException e) {
            throw new IllegalStateException(e);
        }
        this.refreshJwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();
    }

    /**
     * 生成Token
     * @param authorizationCode
     * @return
     */
    public AccessTokenResponse generateToken(AuthorizationCode authorizationCode) {
        Date issueTime = new Date();
        Duration expiresIn = Duration.ofHours(24);
        Date expirationTime = Date.from(issueTime.toInstant()
                .plus(expiresIn));
        String accessTokenId = UUID.randomUUID().toString();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(authorizationCode.getUserId().toString())
                .audience(authorizationCode.getClientId())
                .issuer(serverMetadata.getIssuer())
                .issueTime(issueTime)
                .jwtID(accessTokenId)
                .expirationTime(expirationTime)
                .claim("scp", authorizationCode.getScope())
                .claim("role", authorizationCode.getRole())
                .build();
        JWTClaimsSet refreshClaims = new JWTClaimsSet.Builder()
                .subject(authorizationCode.getUserId().toString())
                .audience(authorizationCode.getClientId())
                .issuer(serverMetadata.getIssuer())
                .issueTime(issueTime)
                .jwtID(accessTokenId)
                .claim("scp", authorizationCode.getScope())
                .claim("role", authorizationCode.getRole())
                .expirationTime(Date.from(issueTime.toInstant()
                        .plus(30, ChronoUnit.DAYS)))
                .build();
        SignedJWT accessJWT = new SignedJWT(accessJwsHeader, claimsSet);
        SignedJWT refreshJWT = new SignedJWT(refreshJwsHeader, refreshClaims);
        try {
            accessJWT.sign(accessKeySigner);
            refreshJWT.sign(refreshKeySigner);
            return AccessTokenResponse.builder()
                    .accessToken(accessJWT.serialize())
                    .tokenType("bearer")
                    .refreshToken(refreshJWT.serialize())
                    .expiresIn(expiresIn.toSeconds())
                    .scope(authorizationCode.getScope().toString())
                    .build();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }
}
