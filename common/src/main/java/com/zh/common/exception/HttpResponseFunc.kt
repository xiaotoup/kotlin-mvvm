package com.zh.common.exception

import com.zh.common.exception.ExceptionHandler.handleException
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * 这个拦截器主要是将异常信息转化为用户”能看懂”的友好提示
 */
class HttpResponseFunc<T> : Function<Throwable, Observable<T>> {

    override fun apply(throwable: Throwable): Observable<T> {
        return Observable.error(handleException(throwable))
    }
}