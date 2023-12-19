package com.wojucai.configuration.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wojucai.Result;
import com.wojucai.configuration.context.RoleContext;
import com.wojucai.configuration.context.UserContext;
import com.wojucai.entity.Bo.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wojucai.entity.codeEnum.ParamConstants.NORMAL;

/**
 * @description: 权限校验拦截
 * @author: xuyujie
 * @date: 2023/08/22
 **/
@Component
@Slf4j
public class GrantInterceptor implements HandlerInterceptor {

    private final PathMatcher pathMatcher;
    private final RoleContext roleContext;

    public GrantInterceptor(PathMatcher pathMatcher, RoleContext roleContext) {
        this.pathMatcher = pathMatcher;
        this.roleContext = roleContext;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,List<String>> roles = roleContext.getRole();

        String uri = request.getRequestURI();
        Authorization authorizationCode = UserContext.getAuthorizationCode();
        String role = authorizationCode.getRole();
        // 判断uri和角色的是否匹配
        // 判断默认的不需要登录的方式
        List<String> paths = roles.getOrDefault(NORMAL, new ArrayList<String>());
        if (hasPermission(uri, paths)) {
            return true;
        }
        paths = roles.getOrDefault(role, new ArrayList<String>());
        if (hasPermission(uri, paths)) {
            return true;
        }
        hasNoPermission(response);
        return false;
    }

    private boolean hasPermission(String uri, List<String> paths) {
        for (String pattern : paths) {
            if (pathMatcher.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }

    private void hasNoPermission(HttpServletResponse response) throws IOException {
        String jsonMap = new ObjectMapper().writeValueAsString(Result.fail("未登录！"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonMap);
    }


}
