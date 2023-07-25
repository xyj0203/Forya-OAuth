package com.wojucai.entity.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/05/26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthReq {

    /**
     * 授权类型
     */
    private String response_type;

    /**
     * 客户端id
     */
    private String client_id;

    /**
     * 重定向链接
     */
    private String redirect_uri;
}
