package com.wojucai.service;

import com.wojucai.Result;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户端服务
 */
@FeignClient(
        name = "resource-server",
        url = "${feign.resource-server-url}" + "${feign.sys-client-url}"
)
public interface ClientService {

    /**
     * 通过客户端名称查询
     * @return
     */
    @GetMapping("/queryClientByName")
    Result queryClientByName(@SpringQueryMap ClientQuery clientQuery);

    /**
     * 添加客户端
     * @param client 客户端对象
     * @return
     */
    @PostMapping("/insertClient")
    Result insertClient(@RequestBody Client client);

    /**
     * 更新客户端信息
     * @param client 客户端对象
     * @return
     */
    @PutMapping("/updateClient")
    Result updateClient(@RequestBody Client client);

    /**
     * 通过id删除
     * @param id id信息
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    Result deleteById(@PathVariable(value = "id") Integer id);

    /**
     * 通过id批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteByIds")
    Result deleteByIds(List<Integer> ids);

    /**
     * 通过id获取
     * @param clientQuery
     * @return
     */
    @GetMapping("/queryById")
    Result queryById(@SpringQueryMap ClientQuery clientQuery);

    /**
     * 查询所有
     * @param clientQuery 条件信息
     * @return
     */
    @GetMapping("/queryAll")
    Result queryAll(@SpringQueryMap ClientQuery clientQuery);

    /**
     * 更改状态
     * @param id id
     * @param enable 是否启用
     * @return
     */
    @PutMapping("/changeEnable")
    Result changeEnable(@RequestParam("id") Integer id, @RequestParam("enable") Integer enable);

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/queryScopeAll")
    Result queryScopeAll();

    /**
     * 根据客户端Id查询客户端
     * @param clientId
     * @return
     */
    @GetMapping("/queryClientById/{clientId}")
    Result queryClientById(@PathVariable(value = "clientId")String clientId);

    /**
     * 根据客户端id查找
     * @param clientId 客户端id
     * @param userId 用户id
     * @return
     */
    @GetMapping("/queryConsentById")
    Result queryConsentById(@RequestParam("clientId")String clientId, @RequestParam("userId") Integer userId);

    /**
     * 查询构建授权页面的元素
     * @param clientId 客户端Id
     * @return
     */
    @GetMapping("/queryClientScope")
    Result queryClientScope(@RequestParam("clientId") String clientId, @RequestParam("userId") Long userId);
}
