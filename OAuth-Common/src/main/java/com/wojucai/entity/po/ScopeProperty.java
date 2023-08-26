package com.wojucai.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description:作用域属性
 * @author: xuyujie
 * @date: 2023/08/05
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_property")
public class ScopeProperty extends BaseEntity{

    /**
     * id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 属性
     */
    private String property;

    /**
     * 类Id
     */
    private Integer classId;

    /**
     * 读写的权限
     */
    private Integer behavior;

    /**
     * 描述
     */
    private String description;
}
