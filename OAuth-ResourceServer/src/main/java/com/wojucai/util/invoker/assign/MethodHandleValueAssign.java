package com.wojucai.util.invoker.assign;

import com.wojucai.util.invoker.exception.MethodExecuteException;
import com.wojucai.util.invoker.support.ConfigSupport;

import java.lang.invoke.MethodHandle;
import java.util.*;

/**
 * @description: 基于MethodHandle的实现
 * @author: xuyujie
 * @date: 2023/12/18
 **/
public class MethodHandleValueAssign extends DynamicValueAssign{

    private final Map<String, Map<String, MethodHandle>> handleMap;

    public MethodHandleValueAssign(Map<String, Set<String>> getTable,
                                   Map<String, Set<String>> setTable,
                                   ConfigSupport configSupport,
                                   Map<String, Map<String, MethodHandle>> handleMap) {
        super(getTable, setTable, configSupport);
        this.handleMap = handleMap;
    }

    @Override
    public void invokeSetMethod(Object obj, String property, Object ...params) {
        String className = obj.getClass().getName();
        Map<String, MethodHandle> methodHandleMap = handleMap.get(className);
        MethodHandle handle = methodHandleMap.get(configSupport.buildSetMethodName(property));
        try {
            handle.invoke(obj, params[0]);
        } catch (Throwable throwable) {
            throw new MethodExecuteException("method execute exception, please check if method exist!");
        }
    }

    @Override
    public Object invokeGetMethod(Object obj, String property) {
        String className = obj.getClass().getName();
        Map<String, MethodHandle> methodHandleMap = handleMap.get(className);
        MethodHandle handle = methodHandleMap.get(configSupport.buildGetMethodName(property));
        try {
            return handle.invoke(obj);
        } catch (Throwable throwable) {
            throw new MethodExecuteException("method execute exception, please check if method exist!");
        }
    }
}
