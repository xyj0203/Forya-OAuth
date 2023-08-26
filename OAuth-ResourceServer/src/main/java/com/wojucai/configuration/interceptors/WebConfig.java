package com.wojucai.configuration.interceptors;

import com.wojucai.OAuth2Client;
import com.wojucai.configuration.context.RoleContext;
import com.wojucai.configuration.context.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/20
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private OAuth2Client oAuth2Client;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RoleContext context;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MethodInterceptor(redisTemplate)).addPathPatterns("/**");
        registry.addInterceptor(new TokenInterceptor(oAuth2Client, redisTemplate)).addPathPatterns("/**");
        registry.addInterceptor(new GrantInterceptor(pathMatcher(),context)).addPathPatterns("/**");
    }

    @Bean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }
}
