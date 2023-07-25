package com.wojucai.entity.reqParam;

/**
 * @description:
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
    private String clientName;

    /**
     * 客户端id;
     */
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
