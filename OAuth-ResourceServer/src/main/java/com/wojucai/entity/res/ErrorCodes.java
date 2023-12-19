package com.wojucai.entity.res;

/**
 *  构造请求失败枚举
 */
public enum ErrorCodes {
    /**
     * The request is missing a required parameter, includes an
     *          unsupported parameter or parameter value, repeats the same
     *          parameter, uses more than one method for including an access
     *          token, or is otherwise malformed.  The resource server SHOULD
     *          respond with the HTTP 400 (Bad Request) status code.
     */
    INVALID_REQUEST(400,"INVALID_REQUEST"),

    /**
     * The access token provided is expired, revoked, malformed, or
     *          invalid for other reasons.  The resource SHOULD respond with
     *          the HTTP 401 (Unauthorized) status code.  The client MAY
     *          request a new access token and retry the protected resource
     *          request.
     */
    INVALID_TOKEN(401, "INVALID_TOKEN"),

    /**
     * The request requires higher privileges than provided by the
     *          access token.  The resource server SHOULD respond with the HTTP
     *          403 (Forbidden) status code and MAY include the "scope"
     *          attribute with the scope necessary to access the protected
     *          resource.
     */
    INSUFFICIENT_SCOPE(403, "INSUFFICIENT_SCOPE");

    /**
     *  对应 http 状态码
     */
    private Integer httpCode;

    /**
     * 对应响应信息
     */
    private String message;

    ErrorCodes(Integer httpCode, String message) {
        this.httpCode = httpCode;
        this.message = message;
    }
}
