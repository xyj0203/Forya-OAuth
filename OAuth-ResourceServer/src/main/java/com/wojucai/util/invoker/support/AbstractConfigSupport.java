package com.wojucai.util.invoker.support;

import java.util.*;

/**
 * @description: 抽象类，抽象共有功能
 * @author: xuyujie
 * @date: 2023/12/18
 **/
public abstract class AbstractConfigSupport implements ConfigSupport{

    /**
     * 构建 GET方法前缀
     */
    protected static final String GET_PREFIX = "get";

    /**
     * 构建 SET方法前缀
     */
    protected static final String SET_PREFIX = "set";

    /**
     * set方法名缓存
     */
    protected Map<String, Set<String>> setMethodTable;

    /**
     * get方法名缓存
     */
    protected Map<String, Set<String>> getMethodTable;

    /**
     * 属性表
     */
    protected Map<String, Set<String>> propertyTable;

    public AbstractConfigSupport(Map<String, Set<String>> propertyTable) {
        this.setMethodTable = buildPropertyTable(propertyTable,MethodAssemble.SET_METHOD);
        this.getMethodTable = buildPropertyTable(propertyTable,MethodAssemble.GET_METHOD);
        this.propertyTable = propertyTable;
    }

    /**
     * 构建Set方法名
     *
     * @param property 属性
     * @return
     */
    public String buildSetMethodName(String property) {
        String upperFirstChar = upperFirstChar(property);
        return SET_PREFIX + upperFirstChar;
    }

    /**
     * 构建Get方法名
     * @param property 属性
     * @return
     */
    public String buildGetMethodName(String property) {
        String upperFirstChar = upperFirstChar(property);
        return GET_PREFIX + upperFirstChar;
    }

    /**
     * 将首位字母转为大写
     * @param property 属性
     * @return
     */
    private String upperFirstChar(String property) {
        char[] array = property.toCharArray();
        array[0] -= 32;
        return new String(array);
    }

    /**
     * 构造 get 或 set 方法映射
     * @param map 原生的字段
     * @param methodAssemble 构造类型
     * @return 构造的类和方法的映射
     */
    private Map<String, Set<String>> buildPropertyTable(Map<String, Set<String>> map, MethodAssemble methodAssemble) {
        Map<String, Set<String>> propertyTable = new HashMap<>(map.size());
        switch (methodAssemble) {
            case GET_METHOD:
                map.forEach(
                        (k, v) -> {
                            Set<String> methods = new HashSet<>(v.size());
                            v.forEach(
                                    property -> {
                                        methods.add(buildGetMethodName(property));
                                    }
                            );
                            propertyTable.put(k, methods);
                        }
                );
                break;
            case SET_METHOD:
                map.forEach(
                        (k, v) -> {
                            Set<String> methods = new HashSet<>(v.size());
                            v.forEach(
                                    property -> {
                                        methods.add(buildSetMethodName(property));
                                    }
                            );
                            propertyTable.put(k, methods);
                        }
                );
                break;
        }
        return propertyTable;
    }

    @Override
    public boolean propertyIsExist(String className, String property) {
        boolean flag = propertyTable.containsKey(className);
        if (!flag ||
                !propertyTable.get(className).contains(property)) {
            return false;
        }
        return true;
    }

    /**
     * 组装的枚举类
     */
    private enum MethodAssemble {
        NORMAL(0),
        GET_METHOD(1),
        SET_METHOD(2);
        Integer type;

        MethodAssemble(Integer type) {
            this.type = type;
        }
    }
}
