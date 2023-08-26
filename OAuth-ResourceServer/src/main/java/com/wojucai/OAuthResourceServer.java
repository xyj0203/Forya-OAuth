package com.wojucai;

import com.wojucai.configuration.context.RoleContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @description:启动类
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@SpringBootApplication(scanBasePackages = "com.wojucai")
@EnableJpaAuditing
public class OAuthResourceServer {
    public static void main(String[] args) {
        SpringApplication.run(OAuthResourceServer.class, args);
    }

    @Bean
    public OAuth2Client oAuth2Client() {
        OAuth2Client oAuth2Client = OAuth2Client.builder()
                .withClientId("C-BToSFjP67zCh9Q")
                .withClientSecret("XGQlEnhw5r5soDcx")
                .withRedirectUri("http://localhost:8080").build();
        return oAuth2Client;
    }
}
