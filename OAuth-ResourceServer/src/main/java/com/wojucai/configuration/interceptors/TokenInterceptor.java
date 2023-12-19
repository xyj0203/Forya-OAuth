package com.wojucai.configuration.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojucai.OAuth2Client;
import com.wojucai.Result;
import com.wojucai.configuration.context.UserContext;
import com.wojucai.entity.codeEnum.CacheConstant;
import com.wojucai.entity.Bo.Authorization;
import com.wojucai.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 拦截处理Token
 * @author: xuyujie
 * @date: 2023/08/20
 **/
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final OAuth2Client oAuth2Client;

    private final RedisTemplate redisTemplate;

    public TokenInterceptor(OAuth2Client oAuth2Client,
                            RedisTemplate redisTemplate) {
        this.oAuth2Client = oAuth2Client;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        log.info("token:user token is {}", token);
//        if (getIpAddress(request).equals("127.0.0.1") && token == null) {
//            Authorization authorization = new Authorization();
//            authorization.setRole(RoleEnum.ROLE_ADMIN.getRole());
//            UserContext.setAuthorizationCode(authorization);
//            log.info("我是管理员用户");
//            return true;
//        }

        // 测试代码，上面的才是正确代码
       if (token == null) {
            Authorization authorization = new Authorization();
            authorization.setRole(RoleEnum.ROLE_ADMIN.getRole());
            UserContext.setAuthorizationCode(authorization);
            log.info("我是管理员用户");
            return true;
        }

        // 校验token是否为空
        if (token == null) {
            log.info("token为空");
            notLogin(response);
            return false;
        }
        if (!oAuth2Client.verifyToken(token)) {
            log.info("token不合法");
            notValid(response);
            return false;
        }
        Authorization authorization = oAuth2Client.getAuthorization(token);
        if (authorization == null) {
            notValid(response);
            return false;
        }
        String role = (String) this.redisTemplate.opsForHash()
                .get(CacheConstant.ROLE_CACHE,authorization.getRole());
        authorization.setRole(role);
        UserContext.setAuthorizationCode(authorization);
        log.info("用户{}的角色是{}",authorization.getUserId(), authorization.getRole());
        return true;
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x - forwarded - for");
        if (ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy - Client - IP");
        }
        if (ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL - Proxy - Client - IP");
        }
        if (ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 清除
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(UserContext.getAuthorizationCode());
        UserContext.clear();
    }

    private void notValid(HttpServletResponse response) throws IOException {
        String jsonMap = new ObjectMapper().writeValueAsString(Result.fail("token不合法！"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonMap);
    }

    private void notLogin(HttpServletResponse response) throws IOException {
        String jsonMap = new ObjectMapper().writeValueAsString(Result.fail("未登录！"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonMap);
    }
}
