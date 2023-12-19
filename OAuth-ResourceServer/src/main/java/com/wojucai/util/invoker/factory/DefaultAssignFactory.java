package com.wojucai.util.invoker.factory;

import com.wojucai.util.invoker.assign.ValueAssign;
import com.wojucai.util.invoker.support.ConfigSupport;

/**
 * @description: 默认的实现
 * @author: xuyujie
 * @date: 2023/12/18
 **/
public class DefaultAssignFactory implements ValueAssignFactory{

    @Override
    public ValueAssign getValueAssign(ConfigSupport configSupport) {
        return configSupport.buildValueAssign();
    }

}
