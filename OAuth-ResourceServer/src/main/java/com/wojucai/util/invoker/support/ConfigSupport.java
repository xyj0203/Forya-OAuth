package com.wojucai.util.invoker.support;

import com.wojucai.util.invoker.assign.ValueAssign;

/**
 * @description: 配置接口, 配置对应的类
 * @author: xuyujie
 * @date: 2023/12/18
 **/
public interface ConfigSupport {

    /**
     * 构建赋值
     * @return
     */
    ValueAssign buildValueAssign();

    /**
     * 构建set方法名
     * @param property 属性名
     * @return
     */
    String buildSetMethodName(String property);

    /**
     * 构建Get方法名
     * @param property 属性名
     * @return
     */
    String buildGetMethodName(String property);

    /**
     * 检查属性是否村子啊
     * @param className 类名
     * @param property 属性
     * @return 返回是否存在
     */
    boolean propertyIsExist(String className, String property);
}
