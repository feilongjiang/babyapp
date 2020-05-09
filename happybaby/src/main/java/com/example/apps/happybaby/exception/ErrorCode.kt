package com.example.apps.happybaby.exception

/**
 * 错误码接口
 */
interface ErrorCode {
    /**
     * 获取错误码
     * @return
     */
    val code: Int?

    /**
     * 获取错误信息
     * @return
     */
    val message: String?
}