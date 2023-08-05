package com.wojucai.entity.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PropertyAnnotations {

    /**
     * 行为 0 为 读 1为写
     * @return
     */
    int behavior() default 0;

    /**
     * 当前属性的形容
     * @return
     */
    String description() default "";
}
