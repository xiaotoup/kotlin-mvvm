package com.zh.common.exception

/**
 * 服务器异常
 */
class ServerException(val code: Int, val msg: String) : RuntimeException()