package com.zh.common.di

import android.content.Context
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.zh.common.base.BaseApplication
import com.zh.common.base.BaseObserver
import com.zh.common.exception.ApiException
import com.zh.common.exception.ResponseTransformer
import com.zh.common.http.INetService
import com.zh.common.http.OSSUploadUrlBean
import com.zh.common.schedulers.SchedulerProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URLConnection

/**
 * 文件上传类
 * 1、获取阿里云de上传与下载链接
 * 2、获取链接再调起阿里云的接口上传文件或下载文件
 */
class FileUploadModule {

    fun provideFileUpload(): FileUploadModule {
        return FileUploadModule()
    }

    /**
     * 从路径中提取后缀名
     *
     * @param path 文件地址
     * @return
     */
    private fun getFileTypeForPath(path: String): String {
        val file = File(path)
        val fileName = file.name
        return fileName.substring(fileName.lastIndexOf(".") + 1)
    }

    /**
     * 获取阿里云OSS文件上传链接
     *
     * @param filePath 文件路径
     * @param callBack 回调
     */
    fun startUploadFile(
        filePath: String,
        callBack: BaseObserver<OSSUploadUrlBean>
    ) {
        // TODO: 获取文件后缀名
        val suffix = getFileTypeForPath(filePath) //文件后缀
        val contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath) //文件类型
        val clientModule =
            (ClientModule.instance.netRequest(INetService::class.java) as INetService)
        clientModule
            .getOSSUploadUrl(suffix, contentType, SPUtils.getInstance().getString("sessionId"))
            .compose(ResponseTransformer.handleResult())
            .compose(SchedulerProvider.instance.applySchedulers())
            .subscribe(object : BaseObserver<OSSUploadUrlBean>( false) {
                override fun onISuccess(message: String, uploadUrlBean: OSSUploadUrlBean) {
                    if (uploadUrlBean?.bussData != null) {
                        UploadFile(filePath, contentType, uploadUrlBean, callBack)
                    }
                }

                override fun onIError(e: ApiException) {
                    ToastUtils.showShort(e.message)
                    callBack.onError(e)
                }
            })
    }

    /**
     * 获得上传链接开始上传文件
     *
     * @param filePath
     * @param uploadUrlBean
     * @param callBack
     */
    private fun UploadFile(
        filePath: String,
        contentType: String,
        uploadUrlBean: OSSUploadUrlBean,
        callBack: BaseObserver<OSSUploadUrlBean>
    ) {
        var file = File(filePath)
        // 执行
        val requestFile = file.asRequestBody(contentType.toMediaTypeOrNull())
        val uploadUrl = uploadUrlBean.bussData?.let { it.uploadUrl }
        var c = 0
        var n = 0
        while (c < 3) {
            n = uploadUrl!!.indexOf("/", n + 1)
            c++
        }
        val baseUrl: String = uploadUrl!!.substring(0, n) //域名
        //判断域名是否相同，相同则上传
        if (baseUrl == BaseApplication.getApplication().mBaseUrl) {
            val clientModule =
                (ClientModule.instance.netRequest(INetService::class.java) as INetService)
            clientModule
                .upLoadFile(contentType, uploadUrl!!.substring(n + 1), requestFile)
                .compose(SchedulerProvider.instance.applySchedulers())
                .subscribe(object : BaseObserver<String>(false) {
                    override fun onISuccess(message: String, response: String) {
                        callBack.onNext(uploadUrlBean)
                    }

                    override fun onIError(e: ApiException) {
                        callBack.onError(e)
                    }
                })

        } else {
            //不同重新创建单例上传
            val clientModule = ClientModule.instance.netRequestOther(
                INetService::class.java,
                baseUrl
            ) as INetService
            clientModule
                .upLoadFile(contentType, uploadUrl.substring(n + 1), requestFile)
                .compose(SchedulerProvider.instance.applySchedulers())
                .subscribe(object : BaseObserver<String>(false) {
                    override fun onISuccess(message: String, response: String) {
                        callBack.onNext(uploadUrlBean)
                    }

                    override fun onIError(e: ApiException) {
                        callBack.onError(e)
                    }
                })
        }
    }
}