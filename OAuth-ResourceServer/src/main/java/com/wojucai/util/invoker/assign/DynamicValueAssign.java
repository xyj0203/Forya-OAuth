package com.wojucai.util.invoker.assign;

import com.wojucai.util.invoker.assign.ValueAssign;
import com.wojucai.util.invoker.exception.MethodExecuteException;
import com.wojucai.util.invoker.support.ConfigSupport;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:动态赋值，通过属性调用方法
 * @author: xuyujie
 * @date: 2023/12/18
 **/
public abstract class DynamicValueAssign implements ValueAssign {

    /**
     * 可读的缓存列表
     */
    protected Map<String, Set<String>> getTable;

    /**
     * 可写的缓存列表
     */
    protected Map<String, Set<String>> setTable;

    protected ConfigSupport configSupport;

    public DynamicValueAssign(Map<String, Set<String>> getTable, Map<String, Set<String>> setTable, ConfigSupport configSupport) {
        this.getTable = getTable;
        this.setTable = setTable;
        this.configSupport = configSupport;
    }

    /**
     * 校验属性是否存在
     * @param object
     * @param property
     */
    protected void calibration(Object object, String property) {
        String className = object.getClass().getName();
        boolean flag = configSupport.propertyIsExist(className, property);
        if (!flag) {
            throw new MethodExecuteException("method not exist!");
        }
    }

    @Override
    public void assignValueByProperty(Object oldObj, Object newObj, List<String> properties) {
        properties.forEach(
                property -> {
                    invokeSetMethod(newObj, property, invokeGetMethod(oldObj, property));
                }
        );
    }

    @Override
    public void assignValueByProperty(List<Object> oldObjs, List<Object> newObjs, List<String> properties) {
        int n = oldObjs.size();
        for (int i = 0; i < n; i++) {
            Object oldObj = oldObjs.get(i);
            Object newObj = newObjs.get(i);
            assignValueByProperty(oldObj, newObj, properties);
        }
    }

    @Override
    public void assignNullByProperty(Object oldObj, List<String> properties) {
        properties.forEach(
                property -> {
                    invokeSetMethod(oldObj, property, new Object[]{null});
                }
        );
    }

    @Override
    public void assignNullByProperty(List<Object> oldObjs, List<String> properties) {
        oldObjs.forEach(
                obj -> {
                    assignNullByProperty(obj, properties);
                }
        );
    }
}
