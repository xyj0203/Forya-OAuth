package com.wojucai.entity.Bo;

import com.wojucai.entity.po.Property;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private List<Property> scopeList;
}
