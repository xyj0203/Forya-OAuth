package com.wojucai.enums;

public enum RoleEnum {
    ROLE_USER(0, "普通用户"),
    ROLE_ADMIN(1, "管理员");

    private Integer code;
    private String role;

    RoleEnum(Integer code, String role) {
        this.code = code;
        this.role = role;
    }

}
