package com.wojucai.configuration.handler;

import com.wojucai.Result;
import com.wojucai.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return Result.fail(BODY_NOT_MATCH);
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
