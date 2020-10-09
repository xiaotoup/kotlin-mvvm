package com.zh.common.exception

/**
 * API接口访问异常
 */
class ApiException(throwable: Throwable?, var code: Int) :
    Exception(throwable) {
    override var message: String? = null

}