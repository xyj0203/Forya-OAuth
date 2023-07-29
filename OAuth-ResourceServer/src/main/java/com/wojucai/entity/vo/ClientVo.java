package com.wojucai.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wojucai.entity.po.BaseEntity;
import com.wojucai.entity.validate.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:client视图对象
 * @author: xuyujie
 * @date: 2023/07/25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientVo extends BaseEntity {
    /**
     * 自增主键
     */
    private Integer Id;

    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    @JsonIgnore
    private String clientSecret;

    /**
     * 认证成功重定向链接
     */
    private String redirectUrl;

    /**
     * 拥有的作用域
     */
    private List<String> scope;

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
