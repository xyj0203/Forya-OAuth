package com.wojucai.service.impl;

import com.wojucai.dao.ClientRepository;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.service.ClientService;
import com.wojucai.util.converter.ClientConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
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
public class ClientServiceImpl implements ClientService {

    @Resource
    private ClientRepository clientRepository;
    @Resource
    private ClientConverter clientConverter;

    @Override
    public Page<ClientVo> queryByClientName(ClientQuery clientQuery) {
        return queryByClientName(clientQuery.getClientName(), clientQuery.getSort(), clientQuery.getPageNow(), clientQuery.getPageNumber());
    }

    @Override
    public Page<ClientVo> queryByClientName(String clientName, String sort, Integer pageNow, Integer pageSize) {
        //创建分页
        Sort sortOp = Sort.by(sort.equals("ASC")?Sort.Order.asc("clientName"):Sort.Order.desc("clientName"));
        Pageable page = PageRequest.of(pageNow, pageSize, sortOp);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true)
                .withIgnorePaths("id");
        Client client = new Client();
        client.setClientName(clientName);
        //使用client对象和matcher对象创建Example对象
        Example<Client> clientExample = Example.of(client, matcher);
        // 查询到的对象
        Page<Client> pageList = clientRepository.findAll(clientExample, page);
        // 将Client转换为ClientVo
        Page<ClientVo> returnPage = pageList.map(clientConverter);
        return returnPage;
    }

    @Override
    public void deleteById(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Client insertClient(Client client) {
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

    @Override
    public ClientVo queryById(ClientQuery clientQuery) {
        Optional<Client> optionalClient = clientRepository.findById(clientQuery.getId());
        ClientVo clientVo = optionalClient.map(clientConverter).orElse(null);
        return clientVo;
    }

    @Override
    public Page<ClientVo> queryAll(ClientQuery clientQuery) {

        return null;
    }


}
