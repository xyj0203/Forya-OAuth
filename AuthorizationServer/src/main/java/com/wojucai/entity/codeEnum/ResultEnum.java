package com.wojucai.entity.codeEnum;

public enum ResultEnum {
    LoginSuccess(10000, "登录成功!"),
    ParamsIllegal(20000, "参数不合法"),
    UserNotExist(20001, "用户不存在"),
    UserIllegal(20002, "用户不合法"),
    UserLogout(10001, "退出登录成功！")
    ;

    public int code;
    public String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
