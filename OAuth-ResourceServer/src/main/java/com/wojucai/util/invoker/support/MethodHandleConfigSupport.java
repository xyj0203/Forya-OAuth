package com.wojucai.util.invoker.support;

import com.wojucai.util.invoker.assign.MethodHandleValueAssign;
import com.wojucai.util.invoker.assign.ValueAssign;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: MethodHandle配置
 * @author: xuyujie
 * @date: 2023/12/18
 **/
@Slf4j
public class MethodHandleConfigSupport extends AbstractConfigSupport  implements ConfigSupport{

    /**
     * 查找方法签名对象
     */
    private final MethodHandles.Lookup lookup = MethodHandles.lookup();

    public MethodHandleConfigSupport(Map<String, Set<String>> propertyTable) {
        super(propertyTable);
    }

    @Override
    public ValueAssign buildValueAssign() {
        Map<String, Map<String, MethodHandle>> handleMap = new HashMap<>();
        extract(getMethodTable, handleMap);
        extract(setMethodTable, handleMap);
        return new MethodHandleValueAssign(getMethodTable, setMethodTable,this, handleMap);
    }

    private void extract(Map<String, Set<String>> propertyMap, Map<String, Map<String, MethodHandle>> handleMap) {
        propertyMap.forEach(
                (className, propertySet) -> {
                    try {
                        Class<?> classObj = Class.forName(className);
                        Method[] declaredMethods = classObj.getDeclaredMethods();
                        for (Method method : declaredMethods) {
                            MethodHandle methodHandle = null;
                            String methodName = method.getName();
                            if (methodName.startsWith(GET_PREFIX) && propertySet.contains(methodName)) {
                                methodHandle = lookup.findVirtual(classObj, methodName, MethodType.methodType(method.getReturnType()));
                                handleMap.computeIfAbsent(className, map -> new HashMap<>())
                                        .put(methodName, methodHandle);
                            }
                            if (methodName.startsWith(SET_PREFIX) && propertySet.contains(methodName)) {
                                methodHandle = lookup.findVirtual(classObj, methodName, MethodType.methodType(void.class, method.getParameterTypes()));
                                handleMap.computeIfAbsent(className, map -> new HashMap<>())
                                        .put(methodName, methodHandle);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        log.error("class not found {}", className);
                    } catch (NoSuchMethodException e) {
                        log.error("method not found {}", className);
                    } catch (IllegalAccessException e) {
                        log.error("access not allow {}", className);
                    }
                }
        );
    }
}
