package com.zh.common.exception

/**
 * 与服务器约定好的异常
 */
object ERROR {
    /**
     * 未知错误
     */
    const val UNKNOWN = 1000

    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001

    /**
     * 网络错误
     */
    const val NETWORD_ERROR = 1002

    /**
     * 协议出错
     */
    const val HTTP_ERROR = 1003

    /**
     * 证书出错
     */
    const val SSL_ERROR = 1005

    /**
     * 连接超时
     */
    const val TIMEOUT_ERROR = 1006
}