package com.zh.common.http

import com.zh.common.base.bean.BaseResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * 网络服务标记接口
 */
interface INetService {
    /**
     * 文件下载
     *
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    fun download(@Url fileUrl: String): Observable<ResponseBody>

    /**
     * 获取阿里云OSS文件上传链接
     *
     * @param suffix      文件后缀
     * @param contentType 文件类型
     * @return
     */
    @GET("/api/base/app/getOSSUploadUrl/{suffix}")
    fun getOSSUploadUrl(
        @Path("suffix") suffix: String,
        @Query("contentType") contentType: String,
        @Query("sessionId") sessionId: String
    ): Observable<BaseResponse<OSSUploadUrlBean>>

    /**
     * 上传文件
     *
     * @return
     */
    @PUT
    fun upLoadFile(
        @Header("Content-Type") contentType: String,
        @Url url: String,
        @Body description: RequestBody
    ): Observable<String>

    fun  getd() : Deferred<BaseResponse<String>>
}