package com.wojucai.service.impl;

import com.wojucai.dao.ClientRepository;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.service.ClientService;
import com.wojucai.util.TextUtils;
import com.wojucai.util.converter.ClientConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import com.wojucai.entity.vo.ClientVo;

/**
 * @description:客户端服务实现类
 * @author: xuyujie
 * @date: 2023/06/11
 **/
@Slf4j
@Service
public class ClientServiceImpl extends AbstractImpl implements ClientService {

    @Resource
    private ClientRepository clientRepository;
    @Autowired
    private ClientConverter converter;

    @Override
    public Page<ClientVo> queryByClientName(ClientQuery clientQuery) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("client_name", match -> match.contains());
        Client client = new Client();
        client.setClientName(clientQuery.getClientName());
        return query(clientQuery, client, matcher, converter, clientRepository);
    }

    @Override
    public ClientVo queryById(ClientQuery clientQuery) {
        Optional<Client> optionalClient = clientRepository.findById(clientQuery.getId());
        ClientVo clientVo = optionalClient.map(converter).orElse(null);
        return clientVo;
    }

    @Override
    public Page<ClientVo> queryAll(ClientQuery clientQuery) {
        return query(clientQuery,null,null, converter, clientRepository);
    }

    @Override
    public Integer changeEnable(Integer id, Integer enable) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.orElse(null) == null) {
            return 0;
        }
        Client client = optionalClient.get();
        client.setEnable(enable);
        Client save = clientRepository.save(client);
        return save == null ? 0 : 1;
    }

    @Override
    public void deleteById(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client insertClient(Client client) {
        String clientId = TextUtils.generateRandomCode(16);
        String clientSecret = TextUtils.generateRandomCode(16);
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        clientRepository.deleteAllByIdInBatch(ids);
    }
}
