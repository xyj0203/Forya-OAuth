package com.wojucai.util.converter;

import com.wojucai.entity.po.Client;
import com.wojucai.entity.vo.ClientVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @description: 转换器
 * @author: xuyujie
 * @date: 2023/07/29
 **/
@Component
public class ClientConverter implements Function<Client, ClientVo> {
    @Override
    public ClientVo apply(Client client) {
        ClientVo clientVo = new ClientVo();
        BeanUtils.copyProperties(client,clientVo);
        String scope = client.getScope();
        if (scope != null) {
            clientVo.setScope(stringParseList(scope));
        }
        return clientVo;
    }

    private List<String> stringParseList(String listStr) {
        listStr = listStr.replaceAll("[\\[\\](){}]","");
        return Arrays.asList(listStr.split(","));
    }
}
