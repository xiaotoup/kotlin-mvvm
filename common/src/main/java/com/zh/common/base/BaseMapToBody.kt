package com.zh.common.base

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * @author: zxh
 * @date: 2019/5/10
 * @description: 将需要请求的Map转化为RequestBody
 */
object BaseMapToBody {
    /**
     * 将map数据转换为 普通的 json RequestBody
     * @param map 以前的请求参数
     * @return
     */
    fun convertMapToBody(map: Map<*, *>): RequestBody {
        return JSONObject(map).toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
    }

    /**
     * 将map数据转换为图片，文件类型的  RequestBody
     * @param map 以前的请求参数
     * @return 待测试
     */
    fun convertMapToMediaBody(map: Map<*, *>): RequestBody {
        return JSONObject(map).toString()
            .toRequestBody("multipart/form-data; charset=utf-8".toMediaTypeOrNull())
    }
}