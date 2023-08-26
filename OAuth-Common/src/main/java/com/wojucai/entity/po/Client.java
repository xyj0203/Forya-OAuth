package com.wojucai.entity.po;

import com.wojucai.entity.validate.Update;
import com.wojucai.utils.conveter.JpaConverterListJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

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
@DynamicUpdate
public class Client extends BaseEntity{

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
    @Column(updatable = false)
    private String clientId;

    /**
     * 客户端密钥
     */
    @Column(updatable = false)
    private String clientSecret;

    /**
     * 认证成功重定向链接
     */
    @URL(message = "重定向链接不合法")
    @NotBlank(message = "重定向链接不能为空", groups = {Default.class})
    private String redirectUrl;

    /**
     * 拥有的作用域
     */
    @NotEmpty(message = "作用域不合法", groups = {Default.class})
    @Convert(converter = JpaConverterListJson.class)
    private List<Integer> scope;

    /**
     * 对于此客户端的描述
     */
    @NotBlank(message = "客户端描述不能为空", groups = {Default.class})
    private String description;

    /**
     * 客户端名称
     */
    @NotBlank(message = "客户端名称不能为空", groups = {Default.class})
    private String clientName;

    /**
     * 启用 0未启用 1启用
     */
    @NotNull(message = "启用不能为空")
    @Range(max = 1, min = 0, message = "启用不合法", groups = {Default.class})
    private Integer enable = 1;
}
