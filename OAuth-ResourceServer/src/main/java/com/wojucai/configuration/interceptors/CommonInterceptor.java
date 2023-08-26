package com.wojucai.configuration.interceptors;

import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.wojucai.entity.codeEnum.CacheConstant.*;

/**
 * @description: 统计API的使用次数和人数
 * @author: xuyujie
 * @date: 2023/08/21
 **/
@Component
@Slf4j
public class CommonInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier(value = "roleCache")
    private Cache roleCache;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 是否销毁角色缓存
        String roleState = (String) redisTemplate.opsForHash().get(FAIL, ROLE);
        if (roleState.equals(TRUE)) {
            roleCache.invalidateAll();
        }
        return true;
    }
}
