package com.wojucai.service;

import com.wojucai.entity.Client;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.vo.ClientVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    /**
     * 客户端名称模糊查询
     * @param clientName
     * @param pageNow 当前页
     * @param pageSize 条数
     * @return 结果集
     */
    Page<ClientVo> queryByClientName(String clientName, Integer pageNow, Integer pageSize);

    /**
     * 客户端名称模糊查询
     * @param clientName
     * @param pageNow 当前页
     * @param pageSize 条数
     * @param sort 排序方式
     * @return 结果集
     */
    Page<ClientVo> queryByClientName(String clientName, String sort, Integer pageNow, Integer pageSize);

    /**
     * 通过id删除
     * @param id id
     * @return 返回 1 成功 0 失败
     */
    int deleteById(Integer id);

    /**
     * 增加客户端
     * @param client 客户端
     * @return 返回更改后的
     */
    Client insertClient(Client client);

    /**
     * 更新客户端
     * @param client 客户端参数
     * @return 返回更改后的
     */
    Client updateClient(Client client);

    /**
     * 批量删除
     * @param ids id列表
     * @return 返回删除的列表
     */
    void batchDelete(List<Integer> ids);

    /**
     * 根据id查询
     * @param clientQuery
     * @return
     */
    Page<ClientVo> queryById(ClientQuery clientQuery);
}
