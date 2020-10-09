package com.zh.common.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @description :
 * @author :  zh
 * @date : 2019/5/6.
 */
interface GlobeHttpHandler {
    fun onHttpResultResponse(
        httpResult: String?,
        chain: Interceptor.Chain?,
        response: Response?
    ): Response?

    fun onHttpRequestBefore(
        chain: Interceptor.Chain?,
        request: Request?
    ): Request?
}