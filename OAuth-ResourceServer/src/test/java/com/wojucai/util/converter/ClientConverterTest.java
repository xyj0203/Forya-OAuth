package com.wojucai.util.converter;


import com.wojucai.entity.Client;
import com.wojucai.entity.vo.ClientVo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ClientConverterTest {

    public void testConverter() {
        ClientConverter clientConverter = new ClientConverter();
    }

    @Test
    public void testStringParseList() {
        ClientConverter clientConverter = new ClientConverter();
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("user");
        String strs = list.toString();
        Client client = new Client();
        client.setId(1);
        client.setClientId("ddfafawefawfc-fasdfawf");
        client.setClientName("ddd");
        client.setClientSecret("****");
        client.setDescription("test");
        client.setEnable(1);
        client.setRedirectUrl("www.baidu.com");
        client.setScope(strs);
        ClientVo clientVo = clientConverter.apply(client);
        System.out.println(clientVo);
    }
}
