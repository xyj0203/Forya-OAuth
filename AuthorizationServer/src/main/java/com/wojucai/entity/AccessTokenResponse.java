package com.wojucai.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/13
 **/
@Data
@Builder
public class AccessTokenResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;
//    private String scope;
}
