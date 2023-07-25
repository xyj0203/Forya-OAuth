package com.wojucai;

/**
 * @description:OAuth客户端认证异常
 * @author: xuyujie
 * @date: 2023/06/10
 **/
public class OAuthClientException extends RuntimeException{
    /**
     * 构造无参异常
     */
    public OAuthClientException() {
        super();
    }

    /**
     * 具有参数的异常构造
     * @param message 异常信息
     */
    public OAuthClientException(String message) {
        super(message);
    }

    /**
     * 具有构造参数信息和原因的异常
     * @param message 错误的信息
     * @param cause 错误的原因
     */
    public OAuthClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带参的异常
     * @param cause 引起的原因
     */
    public OAuthClientException(Throwable cause) {
        super(cause);
    }
}
