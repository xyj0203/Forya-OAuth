package com.wojucai.entity.po;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: xuyujie
 * @date: 2023/08/21
 **/
@Data
public class Authorization {
    private Long userId;
    private String role;
    private List<Integer> scope;
}
