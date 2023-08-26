package com.wojucai.util;

import com.wojucai.utils.conveter.JpaConverterListJson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    @Test
    public void objToJson() {
        JpaConverterListJson jpaConverterListJson = new JpaConverterListJson();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        String str = jpaConverterListJson.convertToDatabaseColumn(list);
        System.out.println(str);
    }
}
