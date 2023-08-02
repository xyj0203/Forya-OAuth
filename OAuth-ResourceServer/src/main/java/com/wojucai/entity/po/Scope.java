package com.wojucai.entity.po;

import com.wojucai.entity.validate.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * 作用域权限
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_scopes")
public class Scope {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = {Update.class})
    @javax.persistence.Id
    @GeneratedValue
    private Integer id;

    /**
     * 权限域
     */
    private String scope;

    /**
     * 父权限id
     */
    private Integer parentId;

    /**
     * 描述
     */
    private String description;

    /**
     * 0是读权限 1是所有权限
     */
    private Integer state;

    /**
     * 对应的类名
     */
    private String className;

    /**
     * 插入时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
