package com.zh.common.rxerrorhandler

import android.content.Context

/**
 * @description :
 * @author :  zh
 * @date : 2019/5/6.
 */
interface ResponseErrorListener {
    fun hanlderResponseError(
        context: Context?,
        e: Exception?
    )
}