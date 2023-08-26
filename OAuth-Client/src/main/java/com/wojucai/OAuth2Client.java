package com.wojucai;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.wojucai.bean.metadata.OAuthService;
import com.wojucai.bean.metadata.ServerMetadata;
import com.wojucai.bean.metadata.TokenResponse;
import com.wojucai.entity.po.Authorization;
import com.wojucai.entity.po.AuthorizationCode;
import com.wojucai.util.TextUtils;
import feign.Feign;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 客户端
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public class OAuth2Client extends BaseClient{
    private static final Logger log = LoggerFactory.getLogger(OAuth2Client.class);

    /**
     * 客户端Id
     */
    private final String clientId;

    /**
     * 客户端密钥
     */
    private final String clientSecret;

    /**
     * 跳转链接
     */
    private final String redirectUri;

    /**
     * 客户端凭据
     */
    private final String clientAuthorization;

    /**
     * 验证签名密钥
     */
    private final Map<String, JWSVerifier> verifiers = new ConcurrentHashMap<>();

    /**
     * 认证过程自己生成
     */
    private final Map<String, String> states = new ConcurrentHashMap<>();

    /**
     * 获取服务器信息的地址
     */
    private String metaUri = "http://localhost:8081";

    private OAuthService serverMeta = Feign.builder()
            .decoder(gsonDecoder)
            .encoder(gsonEncoder)
            .target(OAuthService.class, metaUri);

    /**
     * OAuth 2.0 服务器元信息，可自动获取
     */
    private ServerMetadata serverMetadata;

    /**
     * 构造器
     *
     * 授权过程是状态化
     * @param clientId
     * @param clientSecret
     * @param redirectUri
     */
    public OAuth2Client(String clientId, String clientSecret, String redirectUri) {
        this.clientId = Objects.requireNonNull(clientId, "clientId 不能为空");
        this.clientSecret = Objects.requireNonNull(clientSecret, "clientSecret 不能为空");
        this.redirectUri = Objects.requireNonNull(redirectUri, "redirectUri 不能为空");
        this.clientAuthorization = basicAuth(clientId, clientSecret);
    }

    /**
     * 构造登录链接
     * @param redirectUri 回调地址， 诱导用户获取code参数
     * @return 链接
     * @throws URISyntaxException
     */
    public String getLoginLink(String redirectUri) throws URISyntaxException {
        ensureServerMetadata();
        if (redirectUri == null) {
            redirectUri = this.redirectUri;
        }
        String state = TextUtils.generateRandomCode(16);
        log.debug("Generated state: {}, with redirect_uri: {}", state, redirectUri);
        states.put(state, redirectUri);
        String authorizationEndpoint = serverMetadata.getAuthorization_endpoint();
        return new URIBuilder(authorizationEndpoint)
                .addParameter("client_id", clientId)
                .addParameter("redirect_uri", redirectUri)
                .addParameter("state", state)
                .addParameter("client_secret",clientSecret)
                .addParameter("response_type", "code")
                .normalizeSyntax()
                .toString();
    }

    /**
     * 构造登录链接
     * @return 链接
     * @throws URISyntaxException
     */
    public String getLoginLink() throws URISyntaxException {
        return getLoginLink(this.redirectUri);
    }

    /**
     * 确保元数据不为空
     */
    private synchronized void ensureServerMetadata() {
        if (serverMetadata != null) {
            return;
        }
        serverMetadata = serverMeta.getServerMetadata();
        log.debug("Got ServerMetadata: {}", serverMetadata);
    }

    /**
     * 判断state是否是此系统生成
     * @param state 参数
     * @return 是则返回true 否则返回false
     */
    public boolean isStateSupported(String state) {
        Objects.requireNonNull(state, "state must not be null");
        return states.containsKey(state);
    }

    /**
     * 请求登录令牌
     * @param code authorization_code 参数
     * @param state state参数
     * @return 返回token对象
     */
    public TokenResponse getAccessToken(String code, String state) {
        Objects.requireNonNull(code, "code must not be null");
        ensureServerMetadata();
        String redirectUri = states.remove(state);
        if (null == redirectUri) {
            throw new OAuthClientException("Invalid state retired: " + state);
        }
        return null;
    }

    public Authorization getAuthorization(String token) {
        if (!verifyToken(token)) {
            throw new OAuthClientException("not valid token");
        }
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Authorization authorizationCode = new Authorization();
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            authorizationCode.setUserId(Long.parseLong(jwtClaimsSet.getSubject()));
            List<Long> list = (List<Long>) jwtClaimsSet.getClaim("scp");
            List<Integer> scope = new ArrayList<>(list.size());
            list.forEach(
                    i ->
                            scope.add(i.intValue())
            );
            authorizationCode.setScope(scope);
            authorizationCode.setRole(((Long)jwtClaimsSet.getClaim("role")).toString());
            return authorizationCode;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OAuthClientException("not valid token");
        }
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public String getClientAuthorization() {
        return this.clientAuthorization;
    }

    public Map<String, JWSVerifier> getVerifiers() {
        return this.verifiers;
    }

    public ServerMetadata getServerMetadata() {
        return this.serverMetadata;
    }

    public String getMetaUri() {
        return this.metaUri;
    }

    public void setServerMetadata(ServerMetadata serverMetadata) {
        this.serverMetadata = serverMetadata;
    }

    public void setMetaUri(String metaUri) {
        this.metaUri = metaUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OAuth2Client that = (OAuth2Client) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(clientSecret, that.clientSecret) && Objects.equals(redirectUri, that.redirectUri) && Objects.equals(clientAuthorization, that.clientAuthorization) && Objects.equals(verifiers, that.verifiers) && Objects.equals(states, that.states) && Objects.equals(serverMetadata, that.serverMetadata) && Objects.equals(metaUri, that.metaUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientSecret, redirectUri, clientAuthorization, verifiers, states, serverMetadata, metaUri);
    }

    @Override
    public String toString() {
        return "OAuth2Client{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", clientAuthorization='" + clientAuthorization + '\'' +
                ", verifiers=" + verifiers +
                ", states=" + states +
                ", serverMetadata=" + serverMetadata +
                ", metaUri='" + metaUri + '\'' +
                '}';
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(Objects.requireNonNull(token,
                    "token must not be null"));
            return verifyToken(signedJWT);
        } catch (java.text.ParseException e) {
            log.debug("Unable to parse the token: ", e);
            return false;
        }
    }

    public boolean verifyToken(SignedJWT signedJWT) {
        Objects.requireNonNull(signedJWT, "signedJWT must not be null");
        if (verifiers.isEmpty()) {
            refreshJWKs();
        }
        try {
            String keyID = signedJWT.getHeader().getKeyID();
            JWSVerifier verifier = verifiers.get(keyID);
            if (verifier == null) {
                log.debug("Unknown key with id: {}", keyID);
                return false;
            }
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            Date now = new Date();
            if (expirationTime == null || now.after(expirationTime)) {
                log.debug("JWT exp time ({}) is after now ({}).", expirationTime, now);
                return false;
            }
            return signedJWT.verify(verifier);
        } catch (java.text.ParseException | JOSEException e) {
            log.debug("Unable to parse the token: ", e);
            return false;
        }
    }

    private synchronized void refreshJWKs() {
        verifiers.clear();
        ensureServerMetadata();
        Iterable<Map<String, Object>> keys
                = (Collection<Map<String, Object>>) serverMeta.getJwks()
                .get("keys");
        for (Map<String, Object> key : keys) {
            String kty = (String) key.get("kty");
            try {
                switch (kty) {
                    case "EC":
                        ECKey ecKey = ECKey.parse(key);
                        ECDSAVerifier ecdsaVerifier = new ECDSAVerifier(ecKey);
                        verifiers.put(ecKey.getKeyID(), ecdsaVerifier);
                        break;
                    case "RSA":
                        RSAKey rsaKey = RSAKey.parse(key);
                        RSASSAVerifier rsassaVerifier = new RSASSAVerifier(rsaKey);
                        verifiers.put(rsaKey.getKeyID(), rsassaVerifier);
                        break;
                    case "oct":
                    default:
                        // never used
                        throw new IllegalStateException("Unsupported Key Type: " + kty);
                }
            } catch (ParseException | JOSEException e) {
                log.warn("Unable to parse JWK: {}", key, e);
            }
        }
    }

    public static OAuth2ClientBuilder builder() {
        return new OAuth2ClientBuilder();
    }

    public static class OAuth2ClientBuilder {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private Gson jsonMapper;

        public OAuth2ClientBuilder() {
        }

        public OAuth2ClientBuilder withClientId(String clientId) {
            this.clientId = Objects.requireNonNull(clientId, "clientId must not be null");
            return this;
        }

        public OAuth2ClientBuilder withClientSecret(String clientSecret) {
            this.clientSecret = Objects.requireNonNull(clientSecret, "clientSecret must not be null");
            return this;
        }

        public OAuth2ClientBuilder withRedirectUri(String redirectUri) {
            this.redirectUri = Objects.requireNonNull(redirectUri, "redirectUri must not be null");
            return this;
        }


        public OAuth2Client build() {
            return new OAuth2Client(clientId, clientSecret, redirectUri);
        }

        @Override
        public String toString() {
            return "OAuth2ClientBuilder{" +
                    "clientId='" + clientId + '\'' +
                    ", clientSecret='" + clientSecret + '\'' +
                    ", redirectUri='" + redirectUri + '\'' +
                    ", jsonMapper=" + jsonMapper +
                    ", build=" + build() +
                    '}';
        }
    }
}
