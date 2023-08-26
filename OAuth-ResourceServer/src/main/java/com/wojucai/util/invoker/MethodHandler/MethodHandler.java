package com.wojucai.util.invoker.MethodHandler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/24
 **/
public class MethodHandler {

    /**
     * 查找方法签名对象
     */
    private static MethodHandles.Lookup lookup = MethodHandles.lookup();

    public static  Object  get(Object target, String propertyName) throws Throwable {
        // 构造 getter 方法名
        String getterName = "get" + capitalize(propertyName);
        // 构造 getter 方法的方法类型
        MethodType getterType = MethodType.methodType(target.getClass().getDeclaredField(propertyName).getType());
        // 查找并返回 getter 方法句柄
        MethodHandle virtual = lookup.findVirtual(target.getClass(), getterName, getterType);
        return virtual.invoke(target);
    }

    public static void set(Object target, String propertyName, Object value) throws Throwable {
        // 构造 setter 方法名
        String setterName = "set" + capitalize(propertyName);
        // 构造 setter 方法的方法类型
        MethodType setterType = MethodType.methodType(void.class, target.getClass().getDeclaredField(propertyName).getType());
        // 查找并返回 setter 方法句柄
        MethodHandle virtual = lookup.findVirtual(target.getClass(), setterName, setterType);
        virtual.invoke(target,value);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
