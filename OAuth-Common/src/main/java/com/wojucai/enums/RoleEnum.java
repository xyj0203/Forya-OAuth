package com.wojucai.enums;

import lombok.Data;

public enum RoleEnum {
    ROLE_USER(0, "USER"),
    ROLE_ADMIN(1, "ADMIN");

    private Integer code;
    private String role;

    RoleEnum(Integer code, String role) {
        this.code = code;
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
