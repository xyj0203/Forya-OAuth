package com.wojucai.entity.Bo;

import com.wojucai.entity.po.ScopeProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.List;

import static com.wojucai.entity.codeEnum.ParamConstants.READ;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/06
 **/
@Data
@AllArgsConstructor
public class ScopeBo {
    /**
     * 主id
     */
    private Integer id;

    /**
     * 权限类型
     */
    private String scopePermission;

    /**
     * 字段列表
     */
    private List<ScopeProperty> scopeList;
}
