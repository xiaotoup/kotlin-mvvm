package com.zh.common.exception

import com.google.gson.JsonParseException
import com.zh.common.R
import com.zh.common.base.BaseApplication
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException

/**
 * 异常处理器
 */
object ExceptionHandler {
    //对应HTTP的状态码
    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504
    @JvmStatic
    fun handleException(e: Throwable?): ApiException {
        val ex: ApiException
        return if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, ERROR.HTTP_ERROR)
            when (e.code()) {
                UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex.message =
                    BaseApplication.getApplication().getString(R.string.http_network_error) //均视为网络错误
                else -> ex.message =
                    BaseApplication.getApplication().getString(R.string.http_network_error)
            }
            ex
        } else if (e is ServerException) {    //服务器返回的错误
            val resultException =
                e
            ex = ApiException(resultException, resultException.code)
            ex.message = resultException.msg
            ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            ex = ApiException(e, ERROR.PARSE_ERROR)
            ex.message =
                BaseApplication.getApplication().getString(R.string.http_parsing_error) //均视为解析错误
            ex
        } else if (e is ConnectException) {
            ex = ApiException(e, ERROR.NETWORD_ERROR)
            ex.message =
                BaseApplication.getApplication().getString(R.string.http_connection_failed) //均视为网络错误
            ex
        } else if (e is SSLHandshakeException) {
            ex = ApiException(e, ERROR.SSL_ERROR)
            ex.message =
                BaseApplication.getApplication().getString(R.string.http_ssl_verification_failed)
            ex
        } else if (e is ConnectTimeoutException) {
            ex = ApiException(e, ERROR.TIMEOUT_ERROR)
            ex.message = BaseApplication.getApplication().getString(R.string.http_connection_timed_out)
            ex
        } else if (e is SocketTimeoutException) { //连接超时
            ex = ApiException(e, ERROR.TIMEOUT_ERROR)
            ex.message = BaseApplication.getApplication().getString(R.string.http_connection_timed_out)
            ex
        } else {
            ex = ApiException(e, ERROR.UNKNOWN)
            ex.message = BaseApplication.getApplication().getString(R.string.http_unknown_error) //未知错误
            ex
        }
    }
}