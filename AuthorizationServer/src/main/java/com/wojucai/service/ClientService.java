package com.wojucai.service;

import com.wojucai.entity.Client;
import com.wojucai.entity.Result;
import com.wojucai.entity.reqParam.ClientQuery;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端服务
 */
@FeignClient(
        name = "resource-server",
        url = "${feign.resource-server-url}"
)
public interface ClientService {

    /**
     * 通过客户端名称查询
     * @return
     */
    @GetMapping("/client/queryClientByName")
    Result queryClientByName(@SpringQueryMap ClientQuery clientQuery);

    /**
     * 添加客户端
     * @param client 客户端对象
     * @return
     */
    @PostMapping("/client/insertClient")
    Result insertClient(@RequestBody Client client);

    /**
     * 更新客户端信息
     * @param client 客户端对象
     * @return
     */
    @PutMapping("/client/updateClient")
    Result updateClient(@RequestBody Client client);

    /**
     * 通过id删除
     * @param id id信息
     * @return
     */
    @DeleteMapping("/client/deleteById/{id}")
    Result deleteById(@PathVariable(value = "id") Integer id);

    /**
     * 通过id批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/client/deleteByIds")
    Result deleteByIds(List<Integer> ids);

    /**
     * 通过id获取
     * @param clientQuery
     * @return
     */
    @GetMapping("/client/queryById")
    Result queryById(@SpringQueryMap ClientQuery clientQuery);

    /**
     * 查询所有
     * @param clientQuery 条件信息
     * @return
     */
    @GetMapping("/client/queryAll")
    Result queryAll(@SpringQueryMap ClientQuery clientQuery);

    /**
     * 更改状态
     * @param id id
     * @param enable 是否启用
     * @return
     */
    @PutMapping("/client/changeEnable")
    Result changeEnable(@RequestParam("id") Integer id, @RequestParam("enable") Integer enable);

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/client/queryScopeAll")
    Result queryScopeAll();
}
