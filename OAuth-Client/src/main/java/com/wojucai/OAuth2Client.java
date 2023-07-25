package com.wojucai;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.RSAKey;
import com.wojucai.bean.metadata.OAuthService;
import com.wojucai.bean.metadata.ServerMetadata;
import com.wojucai.bean.metadata.TokenResponse;
import com.wojucai.util.TextUtils;
import feign.Feign;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
     * OAuth 2.0 服务器元信息，可自动获取
     */
    private ServerMetadata serverMetadata;

    /**
     * 获取服务器信息的地址
     */
    private String metaUri = "http://localhost:10086";

    /**
     * 构造器
     *
     * 授权过程是状态化
     * @param clientId
     * @param clientSecret
     * @param redirectUri
     * @param jsonMapper
     */
    public OAuth2Client(String clientId, String clientSecret, String redirectUri, JsonMapper jsonMapper) {
        this.clientId = Objects.requireNonNull(clientId, "clientId 不能为空");
        this.clientSecret = Objects.requireNonNull(clientSecret, "clientSecret 不能为空");
        this.redirectUri = Objects.requireNonNull(redirectUri, "redirectUri 不能为空");
        this.clientAuthorization = basicAuth(clientId, clientSecret);
    }

    /**
     * 构造登录链接
     * @param redirectUri 回调地址， 诱导用户获取code参数
     * @param scopes 请求的权限
     * @return 链接
     * @throws URISyntaxException
     */
    public String getLoginLink(String redirectUri, Set<Scopes> scopes) throws URISyntaxException {
        Objects.requireNonNull(scopes, "scopes must not be null");
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
                .addParameter("scope", scopes.stream()
                        .map(Scopes::getTitle)
                        .collect(Collectors.joining(" ")))
                .addParameter("response_type", "code")
                .normalizeSyntax()
                .toString();
    }

    /**
     * 确保元数据不为空
     */
    private synchronized void ensureServerMetadata() {
        if (serverMetadata != null) {
            return;
        }
        OAuthService serverMeta = Feign.builder()
                        .decoder(gsonDecoder)
                                .target(OAuthService.class, metaUri);
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

    private synchronized void refreshJWKs() {
        verifiers.clear();
        ensureServerMetadata();
        Iterable<Map<String, Object>> keys
                = null;
        for (Map<String, Object> key : keys) {
            try {
                if ("EC".equals(key)) {
                    ECKey ecKey = ECKey.parse(key);
                    ECDSAVerifier verifier = new ECDSAVerifier(ecKey);
                    verifiers.put(ecKey.getKeyID(), verifier);
                } else if ("RSA".equals(key)) {
                    RSAKey rsaKey = RSAKey.parse(key);
                    RSASSAVerifier verifier = new RSASSAVerifier(rsaKey);
                    verifiers.put(rsaKey.getKeyID(), verifier);
                    throw new IllegalStateException("Unsupported key Type: " + key);
                } else {
                    throw new IllegalStateException("Unsupported key Type: " + key);
                }
            } catch (ParseException | JOSEException e) {
                log.warn("Unable to parse JWK: {}", key, e);
            }
        }
    }

    public static class OAuth2ClientBuilder {
        private String clientId;
        private String clientSecret;
        private String redirectUri;
        private JsonMapper jsonMapper;

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

        public OAuth2ClientBuilder withJsonMapper(JsonMapper jsonMapper) {
            this.jsonMapper = Objects.requireNonNull(jsonMapper, "jsonMapper must not be null");
            return this;
        }

        public OAuth2Client build() {
            return new OAuth2Client(clientId, clientSecret, redirectUri, jsonMapper);
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
