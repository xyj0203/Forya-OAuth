package com.wojucai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:客户端类
 * @author: xuyujie
 * @date: 2023/05/24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_client")
public class Client {

    /**
     * 自增主键
     */
    @javax.persistence.Id
    @GeneratedValue
    private Integer Id;

    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 认证成功重定向链接
     */
    private String redirectUrl;

    /**
     * 拥有的作用域
     */
    private String scope;

    /**
     * 对于此客户端的描述
     */
    private String description;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 启用 0未启用 1启用
     */
    private Integer enable = 1;
}
