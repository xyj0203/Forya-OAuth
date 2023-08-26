package com.wojucai.util.invoker;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/21
 **/
public class MethodInvoker {
    public static Object invokeGetMethod(Object object, String propertyName)  {
        try {
            String methodName = "get" + capitalize(propertyName);
            Class<?> clazz = object.getClass();
            Method method = clazz.getMethod(methodName);
            Object invoke = method.invoke(object);
            return invoke;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void invokeSetMethod(Object object, String propertyName, Object value) {
        try {
            String methodName = "set" + capitalize(propertyName);
            Class<?> clazz = object.getClass();
            Class<?> parameterType = value.getClass();
            Method method = clazz.getMethod(methodName, parameterType);
            method.invoke(object, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char firstChar = str.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return str;
        } else {
            return Character.toUpperCase(firstChar) + str.substring(1);
        }
    }
}
