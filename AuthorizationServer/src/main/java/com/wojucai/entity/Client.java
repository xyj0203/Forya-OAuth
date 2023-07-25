package com.wojucai.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:客户端类
 * @author: xuyujie
 * @date: 2023/05/24
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_client")
public class Client {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
}
