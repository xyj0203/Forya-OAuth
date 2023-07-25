package com.wojucai.utils.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {

    protected final Type _type;

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        _type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return _type;
    }

    @Override
    public int compareTo(TypeReference<T> o) {
        return 0;
    }
}
