package com.zh.common.rxerrorhandler

import android.content.Context

/**
 * @description :通过builder方式创建RxErrorHandler
 * @author :  zh
 * @date : 2019/5/6.
 */
class RxErrorHandler(builder: Builder) {
    private val responseErrorListener: ResponseErrorListener?
    private val context: Context?
    fun handleError(throwable: Throwable?) {
        responseErrorListener!!.hanlderResponseError(context, throwable as Exception?)
    }

    class Builder {
        var responseErrorListener: ResponseErrorListener? = null
        var context: Context? = null
        fun with(context: Context?): Builder {
            this.context = context
            return this
        }

        fun responseErrorListener(responseErrorListener: ResponseErrorListener?): Builder {
            this.responseErrorListener = responseErrorListener
            return this
        }

        fun build(): RxErrorHandler {
            checkNotNull(context) { "context is required" }
            checkNotNull(responseErrorListener) { "responseErrorListener is required" }
            return RxErrorHandler(this)
        }
    }

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    init {
        responseErrorListener = builder.responseErrorListener
        context = builder.context
    }
}