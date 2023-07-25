package com.wojucai.entity.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:登录请求封装
 * @author: xuyujie
 * @date: 2023/05/28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
