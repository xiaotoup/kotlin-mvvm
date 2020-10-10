package com.zh.common.di

import android.app.Application
import android.text.TextUtils
import com.zh.common.http.GlobeHttpHandler
import com.zh.common.http.RequestIntercept
import com.zh.common.rxerrorhandler.ResponseErrorListener
import com.zh.common.rxerrorhandler.RxErrorHandler
import com.zh.common.utils.SpUtil
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @author :  zh
 * @description : 使用构建者模式,模块化组件
 * @date : 2019/5/6.
 */
class ClientModule private constructor(buidler: Buidler) {
    private var mApiUrl: HttpUrl? = null
    private var mHandler: GlobeHttpHandler? = null
    private var mInterceptors: MutableList<Interceptor>?
    private var mErroListener: ResponseErrorListener? = null

    /**
     * @description: 设置baseurl
     */
    init {
        mApiUrl = buidler.apiUrl
        mHandler = buidler.handler
        mInterceptors = buidler.interceptors
        mErroListener = buidler.responseErroListener
    }

    /**
     * 使用请求
     *
     * @param clientModule
     * @return
     */
    fun provideRequestService(clientModule: ClientModule): Retrofit? {
        return clientModule.provideClient(
            clientModule.provideCache(clientModule.provideCacheFile()),
            clientModule.provideIntercept()
        )?.let {
            clientModule.provideBaseUrl()?.let { it1 ->
                clientModule.provideRetrofit(it, it1)
            }
        }
    }

    /**
     * @param cache     缓存
     * @param intercept 拦截器
     * @return
     * @description:提供OkhttpClient
     */
    private fun provideClient(cache: Cache, intercept: Interceptor): OkHttpClient? {
        if (mOkHttpClient == null) {
            synchronized(ClientModule::class.java) {
                if (mOkHttpClient == null) {
                    val okHttpClient = OkHttpClient.Builder()
                    okHttpClient.proxy(Proxy.NO_PROXY)
                    mOkHttpClient = configureClient(okHttpClient, cache, intercept)
                }
            }
        }
        return mOkHttpClient
    }

    /**
     * @param client
     * @param httpUrl
     * @return
     * @description: 提供retrofit
     */
    private fun provideRetrofit(client: OkHttpClient, httpUrl: HttpUrl): Retrofit? {
        if (mRetrofit == null) {
            synchronized(ClientModule::class.java) {
                if (mRetrofit == null) {
                    mRetrofit = configureRetrofit(Retrofit.Builder(), client, httpUrl)
                }
            }
        }
        if (httpUrl.toString() != mRetrofit!!.baseUrl().toString()) {
            mRetrofit = configureRetrofit(Retrofit.Builder(), client, httpUrl)
        }
        return mRetrofit
    }

    private fun provideBaseUrl(): HttpUrl? {
        return mApiUrl
    }

    private fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE.toLong()) //设置缓存路径和大小
    }

    private fun provideIntercept(): Interceptor {
        return RequestIntercept(mHandler) //打印请求信息的拦截器
    }

    /**
     * 提供缓存地址
     */
    private fun provideCacheFile(): File {
        return SpUtil.cacheFile
    }

    /**
     * 提供处理Rxjava错误的管理器
     *
     * @return
     */
    private fun proRxErrorHandler(application: Application?): RxErrorHandler {
        return RxErrorHandler
            .builder()
            .with(application)
            .responseErrorListener(mErroListener)
            .build()
    }

    /**
     * @param builder
     * @param client
     * @param httpUrl
     * @return
     * @description:配置retrofit
     */
    private fun configureRetrofit(
        builder: Retrofit.Builder,
        client: OkHttpClient?,
        httpUrl: HttpUrl?
    ): Retrofit {
        return builder
            .baseUrl(httpUrl) //域名
            .client(client) //设置okhttp
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //使用rxjava
            .addConverterFactory(ScalarsConverterFactory.create()) // 添加String转换器
            .addConverterFactory(GsonConverterFactory.create()) //使用Gson
            .build()
    }

    /**
     * 配置okhttpclient
     *
     * @param okHttpClient
     * @return
     */
    private fun configureClient(
        okHttpClient: OkHttpClient.Builder,
        cache: Cache?,
        intercept: Interceptor?
    ): OkHttpClient {
        val builder = okHttpClient
            .connectTimeout(
                TOME_OUT.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                TOME_OUT.toLong(),
                TimeUnit.SECONDS
            )
            .cache(cache) //设置缓存
            .addNetworkInterceptor(intercept!!)
        if (mInterceptors != null && mInterceptors!!.isNotEmpty()) { //如果外部提供了interceptor的数组则遍历添加
            for (interceptor in mInterceptors!!) {
                builder.addInterceptor(interceptor)
            }
        }
        return builder.build()
    }

    class Buidler {
        var apiUrl = "https://api.github.com/".toHttpUrlOrNull()
        var handler: GlobeHttpHandler? = null
        var interceptors: MutableList<Interceptor> = mutableListOf<Interceptor>()
        var responseErroListener: ResponseErrorListener? = null

        fun baseurl(baseurl: String): Buidler { //基础url
            require(!TextUtils.isEmpty(baseurl)) { "baseurl can not be empty" }
            apiUrl = baseurl.toHttpUrlOrNull()
            return this
        }

        fun globeHttpHandler(handler: GlobeHttpHandler?): Buidler { //用来处理http响应结果
            this.handler = handler
            return this
        }

        fun interceptors(interceptors: MutableList<Interceptor>): Buidler { //动态添加任意个interceptor
            this.interceptors = interceptors
            return this
        }

        fun responseErroListener(listener: ResponseErrorListener?): Buidler { //处理所有Rxjava的onError逻辑
            responseErroListener = listener
            return this
        }

        fun build(): ClientModule {
            checkNotNull(apiUrl) { "baseurl is required" }
            return ClientModule(this)
        }
    }

    companion object {
        private const val TOME_OUT = 10
        const val HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024 //缓存文件最大值为10Mb
        private var mRetrofit: Retrofit? = null
        private var mOkHttpClient: OkHttpClient? = null
        private var clientModule: ClientModule? = null//域名不相同，重新创建域名
    }
}