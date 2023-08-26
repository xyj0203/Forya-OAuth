package com.wojucai.util;

import com.wojucai.entity.po.User;
import com.wojucai.util.invoker.MethodHandler.MethodHandler;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/24
 **/
public class TestMethodHandler {

    MethodHandles.Lookup lookup = MethodHandles.lookup();

    @Test
    void test() throws Throwable {

        User user = new User();
        user.setSex(1);
//        Integer sex = MethodHandler.getGetterMethodHandle(user,"sex");
//        System.out.println(sex);
        MethodHandle getter = getGetterMethodHandle(lookup, user, "sex");

        Integer name = (Integer) MethodHandler.get(user,"sex");
        MethodHandler.set(user,"sex",2);
        System.out.println(name);
//
//        MethodHandle setter = getSetterMethodHandle(lookup, user, "sex");
//        setter.invokeExact(user,new Integer(2));
        System.out.println(user.getSex());

    }

    private static MethodHandle getGetterMethodHandle(MethodHandles.Lookup lookup, Object target, String propertyName) throws Throwable {
        // 构造 getter 方法名
        String getterName = "get" + capitalize(propertyName);

        // 构造 getter 方法的方法类型
        MethodType getterType = MethodType.methodType(target.getClass().getDeclaredField(propertyName).getType());
        // 查找并返回 getter 方法句柄
        return lookup.findVirtual(target.getClass(), getterName, getterType);
    }

    private static MethodHandle getSetterMethodHandle(MethodHandles.Lookup lookup, Object target, String propertyName) throws Throwable {
        // 构造 setter 方法名
        String setterName = "set" + capitalize(propertyName);

        // 构造 setter 方法的方法类型
        MethodType setterType = MethodType.methodType(void.class, target.getClass().getDeclaredField(propertyName).getType());

        // 查找并返回 setter 方法句柄
        return lookup.findVirtual(target.getClass(), setterName, setterType);
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
