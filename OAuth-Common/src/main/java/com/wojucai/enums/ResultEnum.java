package com.wojucai.enums;

import lombok.Data;

public enum ResultEnum {

    RequestSuccess(10000,"请求成功"),
    LoginSuccess(100001, "登录成功!"),
    UserLogout(10002, "退出登录成功！"),
    RequestFail(20000,"请求失败"),
    ParamsIllegal(200001, "参数不合法"),
    UserNotExist(20002, "用户不存在"),
    UserIllegal(20003, "用户不合法"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!")

    ;

    public int code;
    public String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
