package com.wojucai.entity.annotation;

import java.lang.annotation.*;

/**
 * 用于标记需要扫描的类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ScopeAnnotations {
    /**
     * 默认的类名形容
     * @return
     */
    String classDescription();
}
