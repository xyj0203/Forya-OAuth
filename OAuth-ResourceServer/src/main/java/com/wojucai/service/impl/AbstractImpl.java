package com.wojucai.service.impl;

import com.wojucai.entity.reqParam.PageQuery;
import com.wojucai.util.SortUtil;
import com.wojucai.util.TimeUtil;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
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
        Pageable page = sort == null ? PageRequest.of(pageQuery.getPageNow(), pageQuery.getPageNumber()) :
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
        TimeUtil.start();
        // 将Client转换为ClientVo
        Page<R> returnPage = pageList.map(converter);
        TimeUtil.end();
        return returnPage;
    }

    public <T, R> R queryForOne (T entity, ExampleMatcher exampleMatcher, Function<T,R> converter, JpaRepository jpaRepository) {
        Example<T> example = Example.of(entity, exampleMatcher);
        Optional<T> optional = jpaRepository.findOne(example);
        T t = optional.orElse(null);
        R res = null;
        if (t != null) {
            res = converter.apply(t);
        }
        return res;
    }

    public <T> T queryForOne (T entity, ExampleMatcher exampleMatcher, JpaRepository jpaRepository) {
        Example<T> example = Example.of(entity, exampleMatcher);
        Optional<T> optional = jpaRepository.findOne(example);
        T t = optional.orElse(null);
        return t;
    }
}
