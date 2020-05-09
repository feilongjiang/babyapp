package com.example.apps.happybaby.exception;

/**
 * 接口返回状态码枚举
 */
public enum ExceptionStatus implements ErrorCode {
    CONTINUE(100, "Continue"),
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    PROCESSING(102, "Processing"),
    CHECKPOINT(103, "Checkpoint"),
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    NO_CONTENT(204, "No Content"),
    RESET_CONTENT(205, "Reset Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_STATUS(207, "Multi-Status"),
    ALREADY_REPORTED(208, "Already Reported"),
    IM_USED(226, "IM Used"),
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    /**
     * @deprecated
     */
    @Deprecated
    MOVED_TEMPORARILY(302, "Moved Temporarily"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    /**
     * @deprecated
     */
    @Deprecated
    USE_PROXY(305, "Use Proxy"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    PAYMENT_REQUIRED(402, "Payment Required"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    LENGTH_REQUIRED(411, "Length Required"),
    PRECONDITION_FAILED(412, "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    /**
     * @deprecated
     */
    @Deprecated
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
    URI_TOO_LONG(414, "URI Too Long"),
    /**
     * @deprecated
     */
    @Deprecated
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    I_AM_A_TEAPOT(418, "I'm a teapot"),
    /**
     * @deprecated
     */
    @Deprecated
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
    /**
     * @deprecated
     */
    @Deprecated
    METHOD_FAILURE(420, "Method Failure"),
    /**
     * @deprecated
     */
    @Deprecated
    DESTINATION_LOCKED(421, "Destination Locked"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    LOCKED(423, "Locked"),
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    TOO_EARLY(425, "Too Early"),
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
    /*默认失败:500*/
    INTERNAL_SERVER_ERROR(500, "网络异常，请稍后再试"),
    NOT_IMPLEMENTED(501, "方法不被服务器支持"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    LOOP_DETECTED(508, "Loop Detected"),
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
    NOT_EXTENDED(510, "Not Extended"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required"),

    /*参数错误:1000-1999*/

    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_EMPTY(1002, "参数为空"),
    PARAM_IS_EXPIRE(1003, "参数已经过期"),
    PARAM_IS_DIFFERENT(1004, "参数不一致"),
    PARAM_TYPE_ERROR(1005, "参数类型错误"),
    PARAM_FORMAT_ERROR_MOBILE(1006, "手机格式错误"),
    PARAM_FORMAT_ERROR_EMAIL(1007, "邮箱格式错误"),
    PROJECTNAME_IS_EXIST(1101,"项目名称已存在"),
    CONTRACT_IS_EXIST(1102,"合同名称已存在"),

    /*用户错误:2000-2999*/
    USER_NOT_EXIST(2001, "用户不存在"),
    USER_HAS_EXISTED(2002, "用户已存在"),
    USER_PASSWORD_ERROR(2003, "用户密码错误"),
    USER_NAME_OR_PASSWORD_ERROR(2004, "用户名或密码错误"),
    USER_NUMBER_USED(2005, "该号码已经被占用"),
    USER_NOT_LOGIN(2006, "用户未登陆"),
    USER_LOGIN_ERROR(2007, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2008, "账号已被禁用"),
    USER_NOT_EQUIPMENT(2009, "非允许的登陆设备"),


    /*接口异常:3000-3999*/
    EX_SELECT_DATA(3001, "查询数据异常"),
    EX_INSERT_DATA(3002, "新增数据异常"),
    EX_UPDATE_DATA(3003, "修改数据异常"),
    EX_DELETE_DATA(3004, "删除数据异常"),
    EX_EMPTY_DATA(3005, "未查询到有效数据"),
    EX_VALIDATE_CODE(3006, "生成随机验证码异常"),
    EX_DATA_EMPTY(3007, "未查询到有效数据"),

    // 通用异常
    SERVER_ERROR(4000,"程序出错"),
    PAGE_NUM_IS_NULL(4001, "页码不能为空"),
    PAGE_SIZE_IS_NULL(4002, "页数不能为空"),
    ID_IS_NULL(4003, "ID不能为空"),
    SEARCH_IS_NULL(4004, "搜索条件不能为空"),


    //数据库相关
    SQL_ADD_FAIL(5001, "添加失败"),
    SQL_UPDATE_FAIL(5002, "更新失败"),
    SQL_DELETE_FAIL(5003, "删除失败"),
    SQL_SELECT_FAIL(5004, "数据不存在"),


    // 短信相关
    SEND_MASSAGE_FAIL(6001, "发送短消息失败"),
    SEND_MASSAGE_OFTEN(6002, "操作发送短消息太频繁,请稍后再试"),
    MESSAGE_TEMPLATE_UNDEFINED(6003, "短信模板未定义"),

    //支付相关
    CREATE_PAY_ORDER_FAIL(7001, "创建订单支付失败"),
    UPDATE_PAY_ORDER_FAIL(7002, "更新支付订单失败"),
    DEL_PAY_ORDER_FAIL(7003, "更新支付订单失败"),
    PAY_ORDER_NO_EXISTS(7004, "支付订单不存在"),
    REFUND_APPLY_NO_EXISTS(7005, "退款申请不存在"),
    VERIFY_NOT_PASS(7006, "验签"),
    RES_FAIL(7007, "响应失败"),
    PAY_CHANNEL_IS_NULL(7008, "支付渠道不能为空"),
    PAY_CHANNEL_PARAM_ERROR(7009, "支付订单渠道参数错误"),
    ;
    private final Integer code;
    private final String msg;


    ExceptionStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    /**
     * 根据编码查询枚举
     *
     * @param code 编码
     * @return 枚举
     */
    public static ExceptionStatus getByCode(Integer code) {
        for (ExceptionStatus value : ExceptionStatus.values()) {
            if (code.equals(value.getCode())) {
                return value;
            }
        }
        return SERVER_ERROR;
    }

    /**
     * 枚举是否包含此code
     *
     * @param code 枚举code
     * @return
     */
    public static Boolean contains(Integer code) {
        for (ExceptionStatus value : ExceptionStatus.values()) {
            if (code.equals(value.getCode())) {
                return true;
            }
        }
        return false;
    }
}
