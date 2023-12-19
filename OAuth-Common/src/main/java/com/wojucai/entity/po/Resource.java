package com.wojucai.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 资源路径
 * @author: xuyujie
 * @date: 2023/12/18
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_resource")
public class Resource extends BaseEntity{

    /**
     * id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 资源名称
     */
    private String resource_name;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 父资源
     */
    private Integer parentId;

    /**
     * 是否匿名可以访问
     */
    private Integer isAnonymous;
}
