package com.wojucai.configuration.handler;

import com.wojucai.Result;
import com.wojucai.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

import static com.wojucai.enums.ResultEnum.BODY_NOT_MATCH;
import static com.wojucai.enums.ResultEnum.INTERNAL_SERVER_ERROR;

/**
 * @description: 全局异常处理器，用来处理错误异常
 * @author: xuyujie
 * @date: 2023/07/24
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result bizExceptionHandler(BizException e) {
        log.error("发生业务异常！原因是：{}",e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理空指针的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        e.printStackTrace();
        return Result.fail(BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public Result exceptionHandler(BindException e) {
        List<ObjectError> allErrors = e.getAllErrors();
        String error = "服务器繁忙";
        for (ObjectError allError : allErrors) {
            error = allError.getDefaultMessage();
        }
        return Result.fail(error);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public Result exceptionHandler(IllegalArgumentException e) {
        return Result.fail("参数不合法");
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        log.error("未知异常！原因是:",e);
        return Result.fail(INTERNAL_SERVER_ERROR);
    }
}
