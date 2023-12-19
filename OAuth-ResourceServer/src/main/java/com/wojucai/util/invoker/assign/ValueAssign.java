package com.wojucai.util.invoker.assign;

import java.util.List;

public interface ValueAssign {

    /**
     * 调用 set 方法
     * @param obj 对象
     * @param property 属性
     * @param params 属性值
     */
    void invokeSetMethod(Object obj, String property, Object ...params) ;

    /**
     * 调用 get 方法
     * @param obj 对象
     * @param property 属性
     */
    Object invokeGetMethod(Object obj, String property);

    /**
     * 将旧对象的属性设置到新的对象
     * @param oldObj 老对象
     * @param newObj 新对象
     * @param properties 属性
     */
    void assignValueByProperty(Object oldObj, Object newObj, List<String> properties);

    /**
     * 将旧对象的属性设置到新的对象
     * @param oldObj 老对象
     * @param newObj 新对象
     * @param properties 属性
     */
    void assignValueByProperty(List<Object> oldObj, List<Object> newObj, List<String> properties);

    /**
     * 将指定字段赋值为空值
     * @param oldObj 老对象
     * @param properties 属性
     */
    void assignNullByProperty(Object oldObj, List<String> properties);

    /**
     * 将指定字段赋值为空值
     * @param oldObj 老对象
     * @param properties 属性
     */
    void assignNullByProperty(List<Object> oldObj, List<String> properties);
}
