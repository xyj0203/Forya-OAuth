package com.wojucai.exception;

import com.wojucai.enums.ResultEnum;
import lombok.Data;

/**
 * @description: 业务异常
 * @author: xuyujie
 * @date: 2023/07/24
 **/
@Data
public class BizException extends RuntimeException{
    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String message;




    public BizException(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public BizException(ResultEnum resultEnum, Throwable cause) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public BizException(String message) {
        this.message = message;
    }

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizException(Integer code, String message, Throwable cause) {

        this.code = code;
        this.message = message;
    }


    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
