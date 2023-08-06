package com.wojucai.service.impl;

import com.wojucai.dao.ClientRepository;
import com.wojucai.dao.ScopePropertyRepository;
import com.wojucai.dao.ScopeRepository;
import com.wojucai.entity.Bo.ScopeBo;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.Scope;
import com.wojucai.entity.po.ScopeProperty;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.vo.ScopeVo;
import com.wojucai.service.ClientService;
import com.wojucai.util.TextUtils;
import com.wojucai.util.converter.ClientConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.wojucai.entity.vo.ClientVo;

import static com.wojucai.entity.codeEnum.ParamConstants.READ;
import static com.wojucai.entity.codeEnum.ParamConstants.WRITE;

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
    @Resource
    private ScopePropertyRepository scopePropertyRepository;
    @Resource
    private ScopeRepository scopeRepository;

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
    public List<ScopeVo> queryScopeAll() {
        List<Scope> all = scopeRepository.findAll();
        List<ScopeVo> res = new ArrayList<>(all.size());
        for (Scope scope : all) {
            ScopeVo scopeVo = new ScopeVo();
            BeanUtils.copyProperties(scope, scopeVo);
            List<ScopeProperty> scopeProperties = scopePropertyRepository.findByClassId(scope.getId());
            ScopeBo scopeRead = new ScopeBo(scope.getId(), READ, new ArrayList<ScopeProperty>());
            ScopeBo scopeWrite = new ScopeBo(scope.getId(), WRITE, new ArrayList<ScopeProperty>());
            scopeProperties.forEach(scopeProperty -> {
                scopeProperty.setCreateTime(null);
                scopeProperty.setUpdateTime(null);
                if (scopeProperty.getBehavior() == 1) {
                    scopeWrite.getScopeList().add(scopeProperty);
                } else {
                    scopeRead.getScopeList().add(scopeProperty);
                }
            });
            scopeVo.setScopeRead(scopeRead);
            scopeVo.setScopeWrite(scopeWrite);
            res.add(scopeVo);
        }
        System.out.println(res);
        return res;
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
