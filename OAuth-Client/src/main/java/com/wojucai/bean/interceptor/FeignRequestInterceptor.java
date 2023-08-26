package com.wojucai.bean.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/21
 **/
public class FeignRequestInterceptor implements RequestInterceptor {

    private final String token;

    public FeignRequestInterceptor(String token) {
        this.token = token;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("token", token);
    }
}
