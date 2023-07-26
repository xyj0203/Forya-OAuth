package com.wojucai.service.impl;

import com.wojucai.dao.ClientRepository;
import com.wojucai.entity.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wojucai.entity.vo.ClientVo;
import static com.wojucai.entity.codeEnum.ParamConstants.ASC;

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

    @Override
    public Page<ClientVo> queryByClientName(String clientName, Integer pageNow, Integer pageSize) {
        return queryByClientName(clientName, "ASC", pageNow, pageSize);
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
        log.info("1111");
        // 将Client转换为ClientVo
        Page<ClientVo> returnPage = pageList.map(x -> {
            ClientVo clientVo = new ClientVo();
            BeanUtils.copyProperties(x,clientVo);
            String scope = x.getScope();
            if (scope != null) {
                clientVo.setScope(stringParseList(scope));
            }
            return clientVo;
        });
        return returnPage;
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

    @Override
    public Page<ClientVo> queryById(ClientQuery clientQuery) {
        return null;
    }

    private List<String> stringParseList(String listStr) {
        listStr = listStr.replaceAll("[\\[\\](){}]","");
        return Arrays.asList(listStr.split(","));
    }
}
