package com.wojucai.service.impl;

import com.wojucai.dao.ClientRepository;
import com.wojucai.entity.Client;
import com.wojucai.service.ClientService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.wojucai.entity.codeEnum.ParamConstants.ASC;

/**
 * @description:客户端服务实现类
 * @author: xuyujie
 * @date: 2023/06/11
 **/
@Service
public class ClientServiceImpl implements ClientService {

    @Resource
    private ClientRepository clientRepository;

    @Override
    public Page<Client> queryByClientName(String clientName, Integer pageNow, Integer pageSize) {
        return queryByClientName(clientName, "ASC", pageNow, pageSize);
    }

    @Override
    public Page<Client> queryByClientName(String clientName, String sort, Integer pageNow, Integer pageSize) {
        //创建分页
        Sort sortOp = Sort.by(sort.equals("ASC")?Sort.Order.asc("clientName"):Sort.Order.desc("clientName"));
        Pageable page = PageRequest.of(pageNow, pageSize, sortOp);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Client client = new Client();
        client.setClientName(clientName);
        //使用client对象和matcher对象创建Example对象
        Example<Client> clientExample = Example.of(client, matcher);
        Page<Client> pageList = clientRepository.findAll(clientExample, page);
        return pageList;
    }

    @Override
    public int deleteById(Integer id) {
        return clientRepository.deleteByClientId(id);
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
}
