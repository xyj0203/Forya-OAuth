package com.wojucai.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @description: 作用域
 * @author: xuyujie
 * @date: 2023/08/05
 **/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_scope")
public class Scope extends BaseEntity{

    /**
     * id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 类的全限定名
     */
    private String  scopeName;

    /**
     * 描述
     */
    private String scopeDescription;
}
