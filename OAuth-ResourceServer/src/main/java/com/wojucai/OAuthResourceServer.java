package com.wojucai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/05/25
 **/
@SpringBootApplication
@EnableWebMvc
public class OAuthResourceServer {
    public static void main(String[] args) {
        SpringApplication.run(OAuthResourceServer.class, args);
    }
}
