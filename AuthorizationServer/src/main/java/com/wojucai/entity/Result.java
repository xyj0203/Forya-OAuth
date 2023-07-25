package com.wojucai.entity;

import com.wojucai.entity.codeEnum.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:统一返回结果集
 * @author: xuyujie
 * @date: 2023/05/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;
    private String message;
    private Object object;

    /**
     * 带数据的返回结果集
     * @param object
     * @param resultEnum
     * @return
     */
    public static Result success(Object object, ResultEnum resultEnum) {
        Result result = new Result(resultEnum.code, resultEnum.message, object);
        return result;
    }

    /**
     * 不带数据的返回结果集
     * @param resultEnum
     * @return
     */
    public static Result success( ResultEnum resultEnum) {
        Result result = new Result(resultEnum.code, resultEnum.message, null);
        return result;
    }

    /**
     * 带数据的失败返回
     * @param object
     * @param resultEnum
     * @return
     */
    public static Result fail(Object object, ResultEnum resultEnum) {
        Result result = new Result(resultEnum.code, resultEnum.message, object);
        return result;
    }

    /**
     * 不带数据的失败返回
     * @param resultEnum
     * @return
     */
    public static Result fail(ResultEnum resultEnum) {
        Result result = new Result(resultEnum.code, resultEnum.message, null);
        return result;
    }
}
