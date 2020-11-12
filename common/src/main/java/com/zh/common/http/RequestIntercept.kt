package com.zh.common.http

import android.text.TextUtils
import com.zh.common.exception.StatusCode
import com.zh.common.utils.SpUtil
import com.zh.common.utils.ZipHelper
import com.zh.config.ZjConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.charset.Charset

/**
 * @author :  zh
 * @description :
 * @date : 2019/5/6.
 */
class RequestIntercept : Interceptor {
    private val tag = "--okhttp--"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val requestBuffer = Buffer()
        if (request.body != null) {
            request.body?.writeTo(requestBuffer)
        } else {
            Timber.tag(tag).w("request.body() == null")
        }

        //添加sessionId - 除去登录接口
        if (!TextUtils.isEmpty(SpUtil.getStringSF(ZjConfig.sessionId))) {
            request = request.newBuilder()
                .addHeader("sessionId", SpUtil.getStringSF(ZjConfig.sessionId)).build()
        }

        //打印url信息
        Timber.tag(tag).w(
            "Sending Request -> %s %s on %n Params --->  %s%n Connection ---> %s%n Headers ---> %s"
            , request.method
            , request.url
            , if (request.body != null) requestBuffer.readUtf8() else "null"
            , chain.connection()
            , request.headers
        )
        val t1 = System.nanoTime()
        val originalResponse = chain.proceed(request)
        val t2 = System.nanoTime()
        //打印响应时间
        Timber.tag(tag).w(
            "Received response -> %s %s %s in %.1fms%n%s"
            , request.method
            , originalResponse.code, request.url, (t2 - t1) / 1e6
            , originalResponse.headers
        )

        //读取服务器返回的结果
        val responseBody = originalResponse.body
        val source = responseBody?.source()
        source?.apply {
            request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = buffer

            //获取content的压缩类型
            val encoding = originalResponse.headers["Content-Encoding"]
            val clone = buffer.clone()
            val bodyString: String

            //解析response content
            if (encoding != null && encoding.equals("gzip", ignoreCase = true)) { //content使用gzip压缩
                val outputStream = ByteArrayOutputStream()
                clone.writeTo(outputStream)
                val bytes = outputStream.toByteArray()
                bodyString = ZipHelper.instance.decompressForGzip(bytes).toString() //解压
                outputStream.close()
            } else if (encoding != null && encoding.equals(
                    "zlib",
                    ignoreCase = true
                )
            ) { //content使用zlib压缩
                val outputStream = ByteArrayOutputStream()
                clone.writeTo(outputStream)
                val bytes = outputStream.toByteArray()
                bodyString = ZipHelper.instance.decompressToStringForZlib(bytes).toString() //解压
                outputStream.close()
            } else { //content没有被压缩
                var charset = Charset.forName("UTF-8")
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                bodyString = clone.readString(charset)
            }

//        Timber.tag(tag).w(jsonFormat(bodyString));
            Timber.tag(tag).w(bodyString)

            //sessionId失效，去登录
            try {
                val jsonObject = JSONObject(bodyString)
                val code = jsonObject.getInt("code")
                if (code == StatusCode.STATUS_CODE_NO_LOGIN) {
                    SpUtil.setStringSF(ZjConfig.sessionId, "")
                    //ARouter.getInstance().build(ZjConfig.LoginActivity).navigation();
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return originalResponse
    }
}