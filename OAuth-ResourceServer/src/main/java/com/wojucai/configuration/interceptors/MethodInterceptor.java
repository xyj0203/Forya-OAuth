package com.wojucai.configuration.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.wojucai.entity.codeEnum.CacheConstant.REQUEST_COUNT;
import static com.wojucai.entity.codeEnum.CacheConstant.REQUEST_METHOD;

/**
 * @description: 统计API
 * @author: xuyujie
 * @date: 2023/08/23
 **/
@Component
@Slf4j
public class MethodInterceptor implements HandlerInterceptor {

    private final RedisTemplate redisTemplate;

    public MethodInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        redisTemplate.opsForValue().increment(REQUEST_METHOD+method,1L);
        LocalDate localDate = LocalDate.now();
        String date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        redisTemplate.opsForValue().increment(REQUEST_COUNT+date,1L);
        return true;
    }
}
