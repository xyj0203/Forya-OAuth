package com.wojucai.util.invoker.factory;

import com.wojucai.util.invoker.assign.ValueAssign;
import com.wojucai.util.invoker.support.ConfigSupport;

/**
 * 顶层类，定义动态属性赋值的行为
 */
public interface ValueAssignFactory {

    /**
     * 通过配置对象获得赋值对象
     * @param configSupport 配置对象
     * @return 赋值对象
     */
    ValueAssign getValueAssign(ConfigSupport configSupport);
}
