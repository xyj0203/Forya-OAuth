package com.wojucai.entity.vo;

import com.wojucai.entity.Bo.ConsentBo;
import lombok.Data;

import java.util.List;

/**
 * 用户授权的页面
 */
@Data
public class ConsentVo {

    /**
     * 权限的大Id
     */
    private Integer id;

    /**
     * 权限描述
     */
    private String scopeDescription;

    /**
     * 权限列表
     */
    private List<ConsentBo> scopeList;

    public ConsentVo(Integer id, String scopeDescription, List<ConsentBo> scopeList) {
        this.scopeDescription = scopeDescription;
        this.scopeList = scopeList;
        this.id = id;
    }
}
