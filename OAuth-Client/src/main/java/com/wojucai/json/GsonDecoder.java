package com.wojucai.json;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;

import static feign.Util.UTF_8;
import static feign.Util.ensureClosed;

/**
 * @description:Gson解析
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public class GsonDecoder implements Decoder {
    private final Gson gson;

    public GsonDecoder(Iterable<TypeAdapter<?>> adapters) {
        this(GsonFactory.create(adapters));
    }

    public GsonDecoder() {
        this(Collections.<TypeAdapter<?>>emptyList());
    }

    public GsonDecoder(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.status() == 404 || response.status() == 204) {
            return Util.emptyValueOf(type);
        }
        if (response.body() == null) {
            return null;
        }
        Reader reader = response.body().asReader(UTF_8);
        try {
            return gson.fromJson(reader, type);
        } catch (JsonIOException e) {
            if (e.getCause() != null && e.getCause() instanceof IOException) {
                throw IOException.class.cast(e.getCause());
            }
            e.printStackTrace();
            throw e;
        } finally {
            ensureClosed(reader);
        }
    }
}
