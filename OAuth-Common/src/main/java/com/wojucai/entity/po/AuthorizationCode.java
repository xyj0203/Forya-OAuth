package com.wojucai.entity.po;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/13
 **/
@Data
public class AuthorizationCode {
    private Integer consentId;
    private Long userId;
    private String clientId;
    private Integer role;
    private List<Integer> scope;
}
