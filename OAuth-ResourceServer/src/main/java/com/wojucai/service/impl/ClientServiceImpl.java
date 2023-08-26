package com.wojucai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wojucai.dao.ClientRepository;
import com.wojucai.dao.ConsentRepository;
import com.wojucai.dao.ScopePropertyRepository;
import com.wojucai.dao.ScopeRepository;
import com.wojucai.entity.Bo.ConsentBo;
import com.wojucai.entity.Bo.ScopeBo;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.Consent;
import com.wojucai.entity.po.Scope;
import com.wojucai.entity.po.ScopeProperty;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.vo.ConsentVo;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.wojucai.entity.vo.ClientVo;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private ConsentRepository consentRepository;

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
        return res;
    }

    @Override
    public List<Integer> queryConsent(String clientId, Long userId) {
        // 找到了对应的关联关系
        Consent consent = consentRepository.findByClientIdAndAndUserId(clientId, userId);
        if (consent == null) {
            return new ArrayList<>();
        }
        return consent.getScope();
    }

    @Override
    public List<ConsentVo> queryClientScope(String clientId) {
        // 找到了对应的关联关系
        Client client = clientRepository.findByClientId(clientId);
        // 对关系进行分类
        List<ScopeProperty> scopeProperties = scopePropertyRepository.findAllById(client.getScope());
        // 获取对应的类和字段的对应关系
        Map<Integer, List<ScopeProperty>> scopePropertyMap = scopeProperties.stream()
                .collect(Collectors.groupingBy(ScopeProperty::getClassId));
        List<ConsentVo> res = new ArrayList<>(scopePropertyMap.size());
        scopePropertyMap.forEach((key,val)->{
            System.out.println("类Id："+key+" ---属性集： "+val);
            // 获取作用域
            Optional<Scope> optional = scopeRepository.findById(key);
            Scope scope = optional.get();
            Map<String, List<ScopeProperty>> collect = val.stream().collect(Collectors.groupingBy(ScopeProperty::getDescription));
            List<ConsentBo> consentBos = new ArrayList<>(2);
            collect.forEach((description, propertyList) -> {
                ConsentBo consentBo = new ConsentBo();
                consentBo.setPropertyName(description);
                propertyList.forEach(property -> {
                    if (property.getBehavior() == 0) {
                        consentBo.setReadId(property.getId());
                    } else {
                        consentBo.setWriteId(property.getId());
                    }
                });
                consentBos.add(consentBo);
            });
            // 最后合为ConsentVo
            ConsentVo consentVo = new ConsentVo(scope.getId(),scope.getScopeDescription(), consentBos);
            res.add(consentVo);
        });
        return res;
    }

    @Override
    public Client queryClientById(String id) {
        return clientRepository.findByClientId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Integer id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            return;
        }
        clientRepository.deleteById(id);
        consentRepository.deleteByClientId(client.getClientId());
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
        Client client1 = clientRepository.findById(client.getId()).get();
        BeanUtil.copyProperties(client, client1, CopyOptions.create().ignoreNullValue());
        return clientRepository.save(client1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Integer> ids) {
        List<Client> allById = clientRepository.findAllById(ids);
        clientRepository.deleteAllByIdInBatch(ids);
        allById.forEach(
                client -> {
                    consentRepository.deleteByClientId(client.getClientId());
                }
        );
    }
}
