package com.wojucai.util.converter;

import com.wojucai.entity.Client;
import com.wojucai.entity.vo.ClientVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Client->ClientVo转换器
 */
public class ClientConverter implements Function<Client, ClientVo> {
    @Override
    public ClientVo apply(Client client) {
        ClientVo clientVo = new ClientVo();
        BeanUtils.copyProperties(client,clientVo);
        String scope = client.getScope();
        clientVo.setScope(stringParseList(scope));
        return clientVo;
    }

    /**
     * 将字符串转为List
     * @param listStr
     * @return
     */
    private List<String> stringParseList(String listStr) {
        listStr = listStr.replaceAll("[\\[\\](){}]","");
        return Arrays.asList(listStr.split(","));
    }
}
