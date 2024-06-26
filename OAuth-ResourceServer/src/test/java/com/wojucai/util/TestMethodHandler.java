package com.wojucai.util;

import com.wojucai.configuration.scope.ScopeAnalyzeContext;
import com.wojucai.entity.annotation.PropertyAnnotations;
import com.wojucai.entity.annotation.ScopeAnnotations;
import com.wojucai.entity.po.Property;
import com.wojucai.entity.po.Scope;
import com.wojucai.entity.po.User;
import com.wojucai.util.invoker.MethodHandler.MethodHandler;
import com.wojucai.util.invoker.MethodInvoker;
import com.wojucai.util.invoker.assign.ValueAssign;
import com.wojucai.util.invoker.factory.DefaultAssignFactory;
import com.wojucai.util.invoker.factory.ValueAssignFactory;
import com.wojucai.util.invoker.support.MethodHandleConfigSupport;
import com.wojucai.util.invoker.support.ReflectConfigSupport;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.*;

import static com.wojucai.entity.codeEnum.CacheConstant.SCOPE_CACHE;

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

    @Test
    public void testValueAssign() throws ClassNotFoundException {
        // 构造请求列表
        Map<String, Set<String>> propertiesTable = new HashMap<>();
        String className = User.class.getName();
        Class<?> clazz = Class.forName(className);
        ScopeAnnotations scopes = clazz.getAnnotation(ScopeAnnotations.class);
        if (scopes != null) {
            className = clazz.getName();
            Field[]fields = clazz.getDeclaredFields();
            Set<String> propertiesList = new HashSet<>();
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyAnnotations.class)) {
                    propertiesList.add(field.getName());
                }
            }
            propertiesTable.put(className, propertiesList);
        }
        propertiesTable.forEach(
                (k, v) ->
                        System.out.println("key: " + k + "    value:" + v)
        );

        ValueAssignFactory valueAssignFactory = new DefaultAssignFactory();
        ValueAssign valueAssign = valueAssignFactory.getValueAssign(new MethodHandleConfigSupport(propertiesTable));
        User user = new User();
        user.setSex(1);
        Object sex = valueAssign.invokeGetMethod(user, "sex");
        System.out.println("Test invoke get " + sex);
        valueAssign.invokeSetMethod(user, "username", "123");
        System.out.println("Test invoke set " + user);
        List<String> list = new ArrayList<>();
        list.add("username");
        valueAssign.assignNullByProperty(user, list);
        System.out.println(user);
    }

    @Test
    public void testValueAssignByReflect() throws ClassNotFoundException {
        // 构造请求列表
        Map<String, Set<String>> propertiesTable = new HashMap<>();
        String className = User.class.getName();
        Class<?> clazz = Class.forName(className);
        ScopeAnnotations scopes = clazz.getAnnotation(ScopeAnnotations.class);
        if (scopes != null) {
            className = clazz.getName();
            Field[]fields = clazz.getDeclaredFields();
            Set<String> propertiesList = new HashSet<>();
            for (Field field : fields) {
                if (field.isAnnotationPresent(PropertyAnnotations.class)) {
                    propertiesList.add(field.getName());
                }
            }
            propertiesTable.put(className, propertiesList);
        }
        propertiesTable.forEach(
                (k, v) ->
                        System.out.println("key: " + k + "    value:" + v)
        );

        ValueAssignFactory valueAssignFactory = new DefaultAssignFactory();
        ValueAssign valueAssign = valueAssignFactory.getValueAssign(new ReflectConfigSupport(propertiesTable));
        User user = new User();
        user.setSex(1);
        Object sex = valueAssign.invokeGetMethod(user, "sex");
        System.out.println("Test invoke get " + sex);
        valueAssign.invokeSetMethod(user, "username", "123");
        System.out.println("Test invoke set " + user);
        List<String> list = new ArrayList<>();
        list.add("username");
        valueAssign.assignNullByProperty(user, list);
        System.out.println(user);
    }
}
