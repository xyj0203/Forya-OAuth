package com.wojucai.util.invoker.assign;

import com.wojucai.util.invoker.exception.MethodExecuteException;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @description: 基于反射实现
 * @author: xuyujie
 * @date: 2023/12/18
 **/
public class ReflectValueAssign extends DynamicValueAssign{

    /**
     * 方法缓存
     */
    private final Map<String, Map<String, Method>> methodMap;

    public ReflectValueAssign(Map<String, Set<String>> getTable,
                              Map<String, Set<String>> setTable,
                              Map<String, Map<String, Method>> methodMap) {
        super(getTable, setTable);
        this.methodMap = methodMap;
    }

    @Override
    public void invokeSetMethod(Object obj, String property, Object ...params) {
        String className = obj.getClass().getName();
        Map<String, Method> methodHandleMap = methodMap.get(className);
        Method handle = methodHandleMap.get(configSupport.buildSetMethodName(property));
        try {
            handle.invoke(obj);
        } catch (Throwable throwable) {
            throw new MethodExecuteException("method execute exception, please check if method exist!");
        }
    }

    @Override
    public Object invokeGetMethod(Object obj, String property) {
        String className = obj.getClass().getName();
        Map<String, Method> methodHandleMap = methodMap.get(className);
        Method handle = methodHandleMap.get(configSupport.buildSetMethodName(property));
        try {
            return handle.invoke(obj);
        } catch (Throwable throwable) {
            throw new MethodExecuteException("method execute exception, please check if method exist!");
        }
    }
}
