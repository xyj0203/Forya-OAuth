package com.wojucai.configuration.context;

import com.wojucai.entity.po.Authorization;

/**
 * @description: 记录用户的上下文对象
 * @author: xuyujie
 * @date: 2023/08/20
 **/
public class UserContext {

    private static ThreadLocal<Authorization> threadLocal = new ThreadLocal<>();

    public static void setAuthorizationCode(Authorization authorization) {
        threadLocal.set(authorization);
    }

    public static Authorization getAuthorizationCode() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
