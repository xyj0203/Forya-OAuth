package com.wojucai.service;

import com.wojucai.Result;
import com.wojucai.entity.po.Client;
import com.wojucai.entity.po.Scope;
import com.wojucai.entity.po.ScopeProperty;
import com.wojucai.entity.reqParam.ClientQuery;
import com.wojucai.entity.vo.ClientVo;
import com.wojucai.entity.vo.ScopeVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    /**
     * 客户端名称模糊查询
     * ClientQuery clientQuery 查询实体
     * @return 结果集
     */
    Page<ClientVo> queryByClientName(ClientQuery clientQuery);

    /**
     * 通过id删除
     * @return 返回 1 成功 0 失败
     */
    void deleteById(Integer id);

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
    ClientVo queryById(ClientQuery clientQuery);

    /**
     * 查询全部
     * @param clientQuery
     * @return
     */
    Page<ClientVo> queryAll(ClientQuery clientQuery);

    /**
     * 更新状态信息
     * @param id 客户端id
     * @param enable 是否启用
     * @return
     */
    Integer changeEnable(Integer id, Integer enable);

    /**
     * 查询所有作用域
     * @return
     */
    List<ScopeVo> queryScopeAll();
}
