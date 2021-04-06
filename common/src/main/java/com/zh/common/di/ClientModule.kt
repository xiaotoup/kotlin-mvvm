package com.zh.common.di

import com.zh.common.base.BaseApplication
import com.zh.common.http.RequestIntercept
import com.zh.common.utils.LogUtil
import com.zh.common.utils.SpUtil
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
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
class ClientModule private constructor() {

    companion object {
        private const val TOME_OUT = 10
        const val HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024 //缓存文件最大值为10Mb
        private var mRetrofit: Retrofit? = null
        private var mOkHttpClient: OkHttpClient? = null

        val instance: ClientModule by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LogUtil.d("--okhttp--", "网络实例化成功")
            ClientModule()
        }
    }

    /**
     * 外部调同步请求
     */
    fun <T> netRequest(service: Class<*>): T {
        return provideRequestService(instance)?.create(service) as T
    }

    /**
     * 外部调同步请求 - 不同的域名地址（用于直接上行到阿里文件库）
     */
    fun <T> netRequestOther(service: Class<*>, baseUrl: String): T {
        return provideRequestService(instance, baseUrl)?.create(service) as T
    }

    /**
     * 使用请求 - 同步
     *
     * @param clientModule
     * @return
     */
    @Synchronized
    private fun provideRequestService(clientModule: ClientModule): Retrofit? {
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
     * 使用请求 - 同步
     *
     * @param clientModule
     * @return
     */
    private fun provideRequestService(
        clientModule: ClientModule,
        baseUrl: String
    ): Retrofit? {
        return clientModule.provideClient(
            clientModule.provideCache(clientModule.provideCacheFile()),
            clientModule.provideIntercept()
        )?.let {
            clientModule.provideBaseUrlOther(baseUrl)?.let { it1 ->
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
     * 同步
     * @param client
     * @param httpUrl
     * @return
     * @description: 提供retrofit
     */
    @Synchronized
    private fun provideRetrofit(client: OkHttpClient, httpUrl: HttpUrl): Retrofit? {
        if (mRetrofit == null) {
            synchronized(ClientModule::class.java) {
                if (mRetrofit == null) {
                    mRetrofit = configureRetrofit(Retrofit.Builder(), client, httpUrl)
                }
            }
        }
        if (httpUrl.toString() != mRetrofit?.baseUrl().toString()) {
            mRetrofit = configureRetrofit(Retrofit.Builder(), client, httpUrl)
        }
        return mRetrofit
    }

    /**
     * 设置域名地址
     */
    private fun provideBaseUrl(): HttpUrl? {
        return BaseApplication.getApplication().mBaseUrl.toHttpUrl()
    }

    /**
     * 其他不同得域名地址
     */
    private fun provideBaseUrlOther(baseUrl: String): HttpUrl? {
        return baseUrl.toHttpUrl()
    }

    /**
     * 设置缓存路径和大小
     */
    private fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE.toLong())
    }

    /**
     * 打印请求信息的拦截器
     */
    private fun provideIntercept(): Interceptor {
        return RequestIntercept()
    }

    /**
     * 提供缓存地址
     */
    private fun provideCacheFile(): File {
        return SpUtil.cacheFile
    }

    /**
     * 同步
     * @param builder
     * @param client
     * @param httpUrl
     * @return
     * @description:配置retrofit
     */
    @Synchronized
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
        intercept: Interceptor
    ): OkHttpClient {
        val builder = okHttpClient
            .connectTimeout(TOME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TOME_OUT.toLong(), TimeUnit.SECONDS)
            .cache(cache) //设置缓存
            .addNetworkInterceptor(intercept)
        return builder.build()
    }
}