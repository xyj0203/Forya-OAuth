package com.wojucai.service.impl;

import com.wojucai.dao.ClientRepository;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.util.SortUtil;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.function.Function;

/**
 * @description: 抽象的实现类
 * @author: xuyujie
 * @date: 2023/07/29
 **/
public abstract class AbstractImpl {

    public <T, R> Page<R> query(ClientQuery clientQuery, T entity, ExampleMatcher exampleMatcher, Function<T,R> converter, JpaRepository jpaRepository) {
        // 分页和排序
        String sortAsc = clientQuery.getSortAsc();
        String sortDesc = clientQuery.getSortDesc();
        Sort sort = SortUtil.sortMultiColumn(sortAsc, sortDesc);
        System.out.println(clientQuery.getPageNumber() + " " + clientQuery.getPageNow());
        Pageable page = sort == null ? PageRequest.of(clientQuery.getPageNow()-1, clientQuery.getPageNumber()) :
                PageRequest.of(clientQuery.getPageNow(), clientQuery.getPageNumber(), sort);
        Page<T> pageList;
        if (entity == null) {
            pageList = jpaRepository.findAll(page);
        } else {
            //使用client对象和matcher对象创建Example对象
            Example<T> clientExample = Example.of(entity, exampleMatcher);
            // 查询到的对象
            pageList = jpaRepository.findAll(clientExample, page);
        }
        // 将Client转换为ClientVo
        Page<R> returnPage = pageList.map(converter);
        return returnPage;
    }
}
