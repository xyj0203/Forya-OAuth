package com.wojucai.entity.Bo.key;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/19
 **/
@Data
public
class ConsentKey implements Serializable {
    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 用户id
     */
    private Long userId;
}
