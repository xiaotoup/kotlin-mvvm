package com.zh.common.exception

import com.zh.common.base.bean.BaseResponse
import io.reactivex.functions.Function

/**
 * 拦截服务器返回的错误
 *
 *
 * 只返回有效数据data，不返回msg、code等数据
 */
class ServerResponseFunc<T> : Function<BaseResponse<T>, T> {
    override fun apply(response: BaseResponse<T>): T {
        //老版的 - 获取外部错误信息
        if (response.code != StatusCode.STATUS_CODE_SUCCESS) {
            throw  ServerException(response.code, response.msg)
        }
        return response as T
    }
}