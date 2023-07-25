package com.wojucai;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum Scopes {

    PROFILE("profile", "您的完整资料"),
    USERNAME("username", "用户名"),
    USER_IMG("userImg", "用户头像"),
    SEX("sex", "性别"),
    AGE("age", "年龄"),
    DESCRIPTION("description", "描述"),
    ROLE("role", "角色");
    @JsonValue
    private final String title;
    private final String explain;

    Scopes(String title, String explain) {
        this.title = title;
        this.explain = explain;
    }

    /**
     * 获取标题
     * @return 标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 获取说明
     * @return 说明
     */
    public String getExplain() {
        return this.explain;
    }

    /**
     * 从Scope标题创建枚举
     * @param title
     * @return 枚举类型
     */
    @JsonCreator
    public static Scopes fromTitle(String title) {
        title = title.trim();
        switch (title) {
            case "profile":
                return PROFILE;
            case "username":
                return USERNAME;
            case "userImg":
                return USER_IMG;
            case "sex":
                return SEX;
            case "age":
                return AGE;
            case "description":
                return DESCRIPTION;
            case "role":
                return ROLE;
            default:
                throw new IllegalArgumentException(title + ": not a valid scope");
        }
    }

    public static Set<Scopes> parseScope(String scopes) {
        if (scopes == null || scopes.trim().isEmpty()) {
            return Collections.emptySet();
        }
        return Arrays.stream(scopes.split("\\s+"))
                .map(String::trim)
                .map(Scopes::fromTitle)
                .collect(Collectors.toSet());
    }
}
