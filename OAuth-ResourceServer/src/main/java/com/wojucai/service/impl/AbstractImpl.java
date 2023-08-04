package com.wojucai.service.impl;

import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.reqParam.PageQuery;
import com.wojucai.util.SortUtil;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.function.Function;

/**
 * @description: 抽象的实现类
 * @author: xuyujie
 * @date: 2023/07/29
 **/
public abstract class AbstractImpl {

    public <T, R> Page<R> query(PageQuery pageQuery, T entity, ExampleMatcher exampleMatcher, Function<T,R> converter, JpaRepository jpaRepository) {
        // 分页和排序
        String sortAsc = pageQuery.getSortAsc();
        String sortDesc = pageQuery.getSortDesc();
        Sort sort = SortUtil.sortMultiColumn(sortAsc, sortDesc);
        System.out.println(pageQuery.getPageNumber() + " " + pageQuery.getPageNow());
        Pageable page = sort == null ? PageRequest.of(pageQuery.getPageNow()-1, pageQuery.getPageNumber()) :
                PageRequest.of(pageQuery.getPageNow(), pageQuery.getPageNumber(), sort);
        Page<T> pageList;
        if (entity == null) {
            pageList = jpaRepository.findAll(page);
        } else {
            //使用client对象和matcher对象创建Example对象
            Example<T> example = Example.of(entity, exampleMatcher);
            // 查询到的对象
            pageList = jpaRepository.findAll(example, page);
        }
        // 将Client转换为ClientVo
        Page<R> returnPage = pageList.map(converter);
        return returnPage;
    }
}
