package com.wojucai.entity.vo;

import com.wojucai.entity.Bo.ScopeBo;
import com.wojucai.entity.po.ScopeProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * @description: ScopeVo对象
 * @author: xuyujie
 * @date: 2023/08/06
 **/
@Data
public class ScopeVo {

    private Integer id;

    /**
     * 类的全限定名
     */
    private String  scopeName;

    /**
     * 描述
     */
    private String scopeDescription;

    /**
     * 读权限
     */
    private ScopeBo scopeRead;

    /**
     * 读权限
     */
    private ScopeBo scopeWrite;
}
