package com.wojucai.configuration.security;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.util.Base64;
import com.wojucai.entity.KeyStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@Configuration
public class KeyStoreConfig {
    private final ObjectMapper objectMapper;

    public KeyStoreConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 读取密钥信息
     *
     * @return 密钥信息
     */
    @Bean
    @SuppressWarnings("unchecked")
    public KeyStore keyStore() {
        var keystorePath = Path.of("keystore.json");
        if (Files.isReadable(keystorePath)) {
            try (var src = Files.newInputStream(keystorePath)) {
                var json = objectMapper.readValue(src, Map.class);
                KeyStore keyStore = new KeyStore();
                keyStore.setRefreshKey(Base64.from(json.get("refreshKey").toString()).decode());
                keyStore.setEcKey(ECKey.parse((Map<String, Object>) json.get("ecKey")));
                return keyStore;
            } catch (IOException | ParseException e) {
                throw new IllegalStateException(e);
            }
        } else {
            var generated = KeyStore.generate();
            try (var out = Files.newOutputStream(keystorePath)) {
                var json = new HashMap<>();
                json.put("refreshKey", generated.getRefreshKey());
                json.put("ecKey", generated.getEcKey().toJSONObject());
                objectMapper.writer(SerializationFeature.INDENT_OUTPUT)
                        .writeValue(out, json);
                return generated;
            } catch (IOException ignored) {
                return generated;
            }
        }
    }
}
