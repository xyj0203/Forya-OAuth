package com.wojucai.configuration;

import com.wojucai.configuration.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:视图配置类
 * @author: xuyujie
 * @date: 2023/05/27
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> loginExclude = new ArrayList<>();
        loginExclude.add("/login");
        loginExclude.add("/userLogin");
        loginExclude.add("/well-known/oauth-authorization-server");
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/*")
                .excludePathPatterns(loginExclude);
    }
}
