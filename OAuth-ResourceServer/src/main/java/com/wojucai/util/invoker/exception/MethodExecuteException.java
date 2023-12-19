package com.wojucai.util.invoker.exception;

/**
 * @description: 方法执行失败异常
 * @author: xuyujie
 * @date: 2023/12/19
 **/
public class MethodExecuteException extends RuntimeException{

    public MethodExecuteException(String message) {
        super(message);
    }
}
