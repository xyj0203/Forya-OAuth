package com.wojucai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:OAuth服务器启动类
 * @author: xuyujie
 * @date: 2023/05/24
 **/
@SpringBootApplication
@MapperScan("com.wojucai.mapper")
public class OAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }
}
