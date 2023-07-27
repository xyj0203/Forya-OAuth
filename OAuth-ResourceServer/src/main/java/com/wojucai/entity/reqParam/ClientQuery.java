package com.wojucai.entity.reqParam;

import com.wojucai.entity.validate.CheckId;
import com.wojucai.entity.validate.CheckString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 客户端查询参数封装
 * @author: xuyujie
 * @date: 2023/06/11
 **/
public class ClientQuery extends PageQuery{

    public ClientQuery(Integer pageNow, Integer pageNumber, String sort) {
        super(pageNow, pageNumber, sort);
    }

    /**
     * 客户端名称
     */
    @NotBlank(message = "名称不能为空", groups = {CheckString.class})
    private String clientName;

    /**
     * 客户端id;
     */
    @NotNull(message = "id不能为空",groups = {CheckId.class})
    private Integer id;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
