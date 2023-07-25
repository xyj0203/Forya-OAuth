package com.wojucai.utils.json;

/**
 * @description: Json序列化失败后的异常
 * @author: xuyujie
 * @date: 2023/06/06
 **/
public class JsonException extends RuntimeException{
    /**
     * 构造不含描述的异常
     */
    public JsonException() {
        super();
    }

    /**
     * 带错误描述的Json异常
     * @param message  抛出的异常信息
     */
    public JsonException(String message) {
        super(message);
    }

    /**
     * 带描述信息和引起异常的原因的构造
     * @param message 抛出的异常信息
     * @param cause 引起异常的原因
     */
    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 只含有引起异常原因的构造
     * @param cause 引起异常的原因
     */
    public JsonException(Throwable cause) {
        super(cause);
    }
}
