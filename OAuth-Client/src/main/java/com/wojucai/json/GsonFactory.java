package com.wojucai.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.wojucai.entity.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static feign.Util.resolveLastTypeParameter;
/**
 * @description:
 * @author: xuyujie
 * @date: 2023/06/10
 **/
final class GsonFactory {
    private GsonFactory() {}

    /**
     * Registers type adapters by implicit type. Adds one to read numbers in a {@code Map<String,
     * Object>} as Integers.
     */
    static Gson create(Iterable<TypeAdapter<?>> adapters) {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        builder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType(),
                new DoubleToIntMapTypeAdapter());
        for (TypeAdapter<?> adapter : adapters) {
            Type type = resolveLastTypeParameter(adapter.getClass(), TypeAdapter.class);
            builder.registerTypeAdapter(type, adapter);
        }
        builder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> {
            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        });
        builder.registerTypeAdapter(Page.class,new PageDeserializer());
        return builder.create();
    }

    static class PageDeserializer<T> implements JsonDeserializer<Page<T>> {

        @Override
        public Page<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            List<T> content = context.deserialize(jsonObject.get("content"), getListType(typeOfT));
            long totalElements = jsonObject.get("totalElements").getAsLong();
            return new PageImpl(content, Pageable.unpaged(), totalElements);
        }

        private Type getListType(Type typeOfT) {
            if (typeOfT instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) typeOfT;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                if (typeArguments.length > 0) {
                    ParameterizedType parameterizedType1 = createParameterizedType(List.class,typeArguments);
                    return parameterizedType1;
                }
            }
            throw new IllegalArgumentException("Unable to determine list type for deserialization");
        }
    }

    private static ParameterizedType createParameterizedType(Type rawType, Type[] typeArguments) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return typeArguments;
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public String getTypeName() {
                StringBuilder typeName = new StringBuilder(rawType.getTypeName());
                if (typeArguments.length > 0) {
                    typeName.append("<");
                    for (int i = 0; i < typeArguments.length; i++) {
                        if (i > 0) {
                            typeName.append(", ");
                        }
                        typeName.append(typeArguments[i].getTypeName());
                    }
                    typeName.append(">");
                }
                return typeName.toString();
            }
        };
    }
}
