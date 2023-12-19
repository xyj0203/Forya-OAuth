package com.wojucai.util.invoker.support;

import com.wojucai.util.invoker.assign.ReflectValueAssign;
import com.wojucai.util.invoker.assign.ValueAssign;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: 反射配置
 * @author: xuyujie
 * @date: 2023/12/18
 **/
@Slf4j
public class ReflectConfigSupport extends AbstractConfigSupport implements ConfigSupport {

    public ReflectConfigSupport(Map<String, Set<String>> propertyTable) {
        super(propertyTable);
    }

    @Override
    public ValueAssign buildValueAssign() {
        Map<String, Map<String, Method>> methodMap = new HashMap<>();
        extract(getMethodTable, methodMap);
        extract(setMethodTable, methodMap);
        return new ReflectValueAssign(getMethodTable, setMethodTable, this, methodMap);
    }

    private void extract(Map<String, Set<String>> propertyMap, Map<String, Map<String, Method>> handleMap) {
        propertyMap.forEach(
                (className, propertySet) -> {
                    Class<?> classObj = null;
                    try {
                        classObj = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        log.error("class not found {}", e);
                    }
                    Method[] declaredMethods = classObj.getDeclaredMethods();
                    for (Method method : declaredMethods) {
                        String methodName = method.getName();
                        if ((methodName.startsWith("get") && propertySet.contains(methodName)) ||
                                methodName.startsWith("set") && propertySet.contains(methodName)) {
                            handleMap.computeIfAbsent(className, map -> new HashMap<>())
                                    .put(methodName, method);
                        }
                    }
                }
        );
    }
}
