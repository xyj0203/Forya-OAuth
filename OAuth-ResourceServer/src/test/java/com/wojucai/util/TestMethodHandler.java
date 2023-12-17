package com.wojucai.util;

import com.wojucai.entity.po.User;
import com.wojucai.util.invoker.MethodHandler.MethodHandler;
import com.wojucai.util.invoker.MethodInvoker;
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

    MethodHandles.Lookup lookup = MethodHandles.publicLookup();

    /**
     * 反射 4-5秒
     * @throws Throwable
     */
    @Test
    void test() throws Throwable {
        TimeUtil.start();
        User user = new User();
        user.setSex(1);
        for (int i = 0; i < 10000000; i++) {
              MethodInvoker.invokeGetMethod(user,"sex");
//            MethodInvoker.invokeSetMethod(user,"sex", 1);
        }
        TimeUtil.end();
    }


    /**
     * MethodHandle 21秒
     * @throws Throwable
     */
    @Test
    void test1() throws Throwable {
        TimeUtil.start();
        User user = new User();
        user.setSex(1);

        MethodType methodType = MethodType.methodType(Integer.class);
        for (int i = 0; i < 10000000; i++) {
              lookup.findVirtual(User.class,"getSex", methodType).invoke(user);
//            MethodHandler.set(user,"sex",2);
        }
        TimeUtil.end();
    }

    /**
     * 直接调用 0.027秒
     * @throws Throwable
     */
    @Test
    void test2() throws Throwable {
        TimeUtil.start();
        User user = new User();
        user.setSex(1);
        for (int i = 0; i < 10000000; i++) {
            user.getSex();
//            user.setSex(1);
        }
        TimeUtil.end();
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
