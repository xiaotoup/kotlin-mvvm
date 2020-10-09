package com.zh.common.exception

import com.zh.common.base.bean.BaseResponse
import io.reactivex.ObservableTransformer

/**
 * @author: zxh
 * @date: 2019/5/24
 * @description: 对返回的数据进行处理，区分异常的情况
 */
object ResponseTransformer {
    fun <T> handleResult(): ObservableTransformer<BaseResponse<T>, T> {
        return ObservableTransformer<BaseResponse<T>, T> { upstream ->
            upstream.map(ServerResponseFunc()).onErrorResumeNext(HttpResponseFunc<T>())
        }
    }
}