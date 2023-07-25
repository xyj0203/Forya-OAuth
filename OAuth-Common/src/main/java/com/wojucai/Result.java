package com.wojucai;



import com.wojucai.enums.ResultEnum;
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
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result(ResultEnum.RequestSuccess.code, ResultEnum.RequestSuccess.message, object);
        return result;
    }

    /**
     * 带数据的返回结果集
     * @param object
     * @param resultEnum
     * @return
     */
    public static Result success(Object object, ResultEnum resultEnum) {
        Result result = new Result(resultEnum.code, "请求成功！", object);
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

    public static Result fail() {
        Result result = new Result(ResultEnum.RequestFail.code, ResultEnum.RequestFail.message, null);
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

    /**
     * 不同构造参数的返回
     * @param code
     * @param message
     * @return
     */
    public static Result fail(Integer code, String message) {
        Result result = new Result(code, message, null);
        return result;
    }

    /**
     * 不同构造参数的返回
     * @param message 错误信息
     * @return
     */
    public static Result fail( String message) {
        Result result = new Result(null, message, null);
        return result;
    }
}
