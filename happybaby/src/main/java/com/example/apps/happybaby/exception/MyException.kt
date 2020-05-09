package com.example.apps.happybaby.exception

class MyException : RuntimeException {
    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    /**
     * 错误码
     */
    val errorCode: ErrorCode

    /**
     * 这个是和谐一些不必要的地方,冗余的字段
     * * 尽量不要用
     */
    private val code: String? = null

    constructor() : super(ExceptionStatus.INTERNAL_SERVER_ERROR.message) {
        errorCode = ExceptionStatus.INTERNAL_SERVER_ERROR
    }

    /**
     * 指定错误码构造通用异常
     *
     * @param errorCode
     */
    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
    }

    /**
     * 指定详细描述构造通用异常
     *
     * @param detailMessage
     */
    constructor(detailMessage: String?) : super(detailMessage) {
        errorCode = ExceptionStatus.INTERNAL_SERVER_ERROR
    }

    /**
     * 指定导火索构造通用异常
     *
     * @param t 导火索
     */
    constructor(t: Throwable?) : super(t) {
        errorCode = ExceptionStatus.INTERNAL_SERVER_ERROR
    }

    /**
     * 构造通用异常
     *
     * @param errorCode     错误码
     * @param detailMessage 详细描述
     */
    constructor(
        errorCode: ErrorCode,
        detailMessage: String?
    ) : super(detailMessage) {
        this.errorCode = errorCode
    }

    /**
     * 构造通用异常
     *
     * @param errorCode 错误码
     * @param t         导火索
     */
    constructor(
        errorCode: ErrorCode,
        t: Throwable?
    ) : super(errorCode.message, t) {
        this.errorCode = errorCode
    }

    /**
     * 构造通用异常
     *
     * @param detailMessage 详细描述
     * @param t             导火索
     */
    constructor(detailMessage: String?, t: Throwable?) : super(detailMessage, t) {
        errorCode = ExceptionStatus.INTERNAL_SERVER_ERROR
    }

    /**
     * 构造通用异常
     *
     * @param errorCode     错误码
     * @param detailMessage 详细描述
     * @param t             导火索
     */
    constructor(
        errorCode: ErrorCode,
        detailMessage: String?,
        t: Throwable?
    ) : super(detailMessage, t) {
        this.errorCode = errorCode
    }

    companion object {
        private const val serialVersionUID = -7864604160297181941L
    }
}