package com.wojucai.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import feign.RequestTemplate;
import feign.codec.Encoder;

import java.lang.reflect.Type;
import java.util.Collections;

/**
 * @description:GSON加密
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public class GsonEncoder implements Encoder{
    private final Gson gson;

    public GsonEncoder(Iterable<TypeAdapter<?>> adapters) {
        this(GsonFactory.create(adapters));
    }

    public GsonEncoder() {
        this(Collections.<TypeAdapter<?>>emptyList());
    }

    public GsonEncoder(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        template.body(gson.toJson(object, bodyType));
    }
}
