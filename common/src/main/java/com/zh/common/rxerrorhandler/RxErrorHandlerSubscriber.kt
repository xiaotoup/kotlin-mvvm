package com.zh.common.rxerrorhandler

import org.reactivestreams.Subscriber

/**
 * @description :通过继承该观察者，实现错误交给RxErrorHandler进行处理。
 * @author :  zh
 * @date : 2019/5/6.
 */
abstract class RxErrorHandlerSubscriber<T>(private val rxErrorHandler: RxErrorHandler) :
    Subscriber<T> {
    override fun onComplete() {}
    override fun onError(e: Throwable) {
        rxErrorHandler.handleError(e)
    }
}