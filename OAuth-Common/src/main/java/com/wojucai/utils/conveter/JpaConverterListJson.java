package com.wojucai.utils.conveter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import java.util.List;

public class JpaConverterListJson implements AttributeConverter<Object, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, List.class);
    }
}
