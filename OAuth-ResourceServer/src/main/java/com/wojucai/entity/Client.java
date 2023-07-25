package com.wojucai.entity;

import com.wojucai.entity.validate.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
    @NotNull(message = "id不能为空", groups = {Update.class})
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
    @URL(message = "重定向链接不合法")
    @NotBlank(message = "重定向链接不能为空")
    private String redirectUrl;

    /**
     * 拥有的作用域
     */
    @NotBlank(message = "作用域不合法")
    private String scope;

    /**
     * 对于此客户端的描述
     */
    @NotBlank(message = "客户端描述不能为空")
    private String description;

    /**
     * 客户端名称
     */
    @NotBlank(message = "客户端名称不能为空")
    private String clientName;

    /**
     * 启用 0未启用 1启用
     */
    @NotNull(message = "启用不能为空")
    @Range(max = 1, min = 0, message = "启用不合法")
    private Integer enable = 1;
}
