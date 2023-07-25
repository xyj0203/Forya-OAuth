package com.wojucai.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 * @description: Jackson具体实现
 * @author: xuyujie
 * @date: 2023/06/06
 **/
public class JacksonJsonMapper implements JsonMapper{

    private final ObjectMapper objectMapper;
    private final ThreadLocal<NamingStrategy> namingStrategy;

    public JacksonJsonMapper() {
        this(new ObjectMapper());
    }

    /**
     * 构造器
     * @param objectMapper
     */
    public JacksonJsonMapper(ObjectMapper objectMapper) {
        this.namingStrategy = new InheritableThreadLocal<>();
        this.namingStrategy.set(NamingStrategy.IDENTITY);
        this.objectMapper = objectMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .configure(JsonParser.Feature.IGNORE_UNDEFINED, true)
                //对于低版本的jackson做适配
                .setPropertyNamingStrategy(new PropertyNamingStrategy(){
                    public String translate(String input) {
                        NamingStrategy namingStrategy = JacksonJsonMapper.this.namingStrategy.get();
                        if (namingStrategy == null) {
                            namingStrategy = NamingStrategy.IDENTITY;
                        }
                        switch (namingStrategy) {
                            case IDENTITY:
                                return input;
                            case LOWER_CASE_WITH_DASHES:
                                return translateLowerCaseWithSeparator(input, '-');
                            case LOWER_CASE_WITH_DOTS:
                                return translateLowerCaseWithSeparator(input, '.');
                            case LOWER_CASE_WITH_UNDERSCORES: {
                                if (input == null) {
                                    return null;
                                }
                                int length = input.length();
                                StringBuilder result = new StringBuilder(length * 2);
                                int resultLength = 0;
                                boolean wasPrevTranslated = false;
                                for (int i = 0; i < length; i++) {
                                    char c = input.charAt(i);
                                    if (i > 0 || c != '-') {
                                        if (Character.isUpperCase(c)) {
                                            if (!wasPrevTranslated && resultLength > 0
                                                    && result.charAt(resultLength - 1) != '-') {
                                                result.append("-");
                                                resultLength++;
                                            }
                                            c = Character.toLowerCase(c);
                                            wasPrevTranslated = true;
                                        } else {
                                            wasPrevTranslated = false;
                                        }
                                        result.append(c);
                                        resultLength++;
                                    }
                                }
                                return resultLength > 0 ? result.toString() : input;
                            }
                            case UPPER_CAMEL_CASE: {
                                if (input == null || input.isEmpty()) {
                                    return input;
                                }
                                char c = input.charAt(0);
                                char uc = Character.toUpperCase(c);
                                if (c == uc) {
                                    return input;
                                }
                                StringBuilder sb = new StringBuilder(input);
                                sb.setCharAt(0, uc);
                                return sb.toString();
                            }
                            case UPPER_CAMEL_CASE_WITH_SPACES:{
                                if (input == null) {
                                    return null;
                                }
                                final int length = input.length();
                                if (length == 0) {
                                    return input;
                                }
                                final StringBuilder result = new StringBuilder(length + (length >> 1));
                                boolean upperConnected = false;
                                for (int i = 0; i < length; i++) {
                                    char ch = input.charAt(i);
                                    char uc = Character.toUpperCase(ch);
                                    if (i == 0) {
                                        result.append(uc);
                                        upperConnected = true;
                                    } else if (uc == ch) {
                                        if (!upperConnected) {
                                            result.append(' ');
                                        }
                                        result.append(uc);
                                        upperConnected = true;
                                    } else {
                                        result.append(ch);
                                        upperConnected = false;
                                    }
                                }
                                return result.toString();
                            }
                            default:
                                throw new IllegalStateException("unexpected value: " + namingStrategy);
                        }
                    }

                    @Override
                    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
                        return translate(defaultName);
                    }

                    @Override
                    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                        return translate(defaultName);
                    }

                    @Override
                    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
                        return translate(defaultName);
                    }

                    @Override
                    public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
                        return translate(defaultName);
                    }

                    private String translateLowerCaseWithSeparator(final String input, final char separator) {
                        if (input == null) {
                            return null;
                        }
                        final int length = input.length();
                        if (length == 0) {
                            return input;
                        }

                        final StringBuilder result = new StringBuilder(length + (length >> 1));
                        int upperCount = 0;
                        for (int i = 0; i < length; i++) {
                            char ch = input.charAt(i);
                            char lc = Character.toLowerCase(ch);

                            if (lc == ch) {
                                if (upperCount > 1){
                                    result.insert(result.length()-1, separator);
                                }
                                upperCount = 0;
                            } else {
                                if ((upperCount == 0) && (i > 0)) {
                                    result.append(separator);
                                }
                                ++upperCount;
                            }
                            result.append(lc);
                        }
                        return result.toString();
                    }
                });
    }

    @Override
    public <T> T parseObject(String jsonString, Class<T> type) throws JsonException {
        try {
            return objectMapper.readValue(jsonString, type);
        }catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public <T> T parseObject(String jsonString, Type typeDefinition) throws JsonException {
        try {
            return objectMapper.readValue(jsonString, new TypeReferenceWrapper<>(typeDefinition));
        }catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public <T> List<T> parseArray(String jsonString, Type collectionType) throws JsonException {
        try {
            return objectMapper.readValue(jsonString, new TypeReferenceWrapper<>(collectionType));
        }catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public <T> String serializeToString(T obj) throws JsonException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage(), e);
        }
    }

    @Override
    public <T> String serializeToString(T obj, Type typeDefinition) throws JsonException {
        return serializeToString(obj);
    }

    @Override
    public void preferNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy.set(Objects.requireNonNull(namingStrategy, "namingStrategy 不能为空"));
    }

    private final static class TypeReferenceWrapper<T> extends TypeReference<T> {

        private final Type type;

        public TypeReferenceWrapper(Type type) {
            this.type = type;
        }

        @Override
        public Type getType() {
            return super.getType();
        }
    }
}
