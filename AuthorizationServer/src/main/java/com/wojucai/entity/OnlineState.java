package com.wojucai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:在线状态
 * @author: xuyujie
 * @date: 2023/05/28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineState {
    /**
     * 在线的角色名
     */
    private Integer roleId;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 是否登录成功
     */
    private Boolean state;
}
