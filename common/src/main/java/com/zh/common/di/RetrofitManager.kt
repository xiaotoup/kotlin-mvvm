package com.zh.common.di

import android.os.Environment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.zh.common.http.LocalCookieJar
import com.zh.common.http.RequestIntercept
import com.zh.config.ZjConfig
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * 网络请求发起的基层调用
 */
class RetrofitManager private constructor() {

    companion object {
        //超时时间
        private const val TOME_OUT = 10

        //缓存文件最大值为10Mb
        private const val HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024

        //多个域名存储请求
        private val serviceMap: ConcurrentHashMap<String, in Any> = ConcurrentHashMap()

        val instance: RetrofitManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            LogUtils.dTag("--okhttp--", "网络实例化成功")
            RetrofitManager()
        }
    }

    /**
     * 获取发起Retrofit实列
     * @param clazz 网路请求的 Interface 类
     * @param hostUrl 域名, 默认ZjConfig.base_url，需要修改传入新的域名（新的每次都传）
     */
    fun <T : Any> apiService(clazz: Class<T>, hostUrl: String = ZjConfig.base_url): T {
        val key = "${clazz.name}_$hostUrl"
        val value: T
        if (serviceMap.containsKey(key)) { //拿取已有实列
            value = serviceMap[key] as T
        } else { //没有直接添加
            value = mRetrofit(hostUrl.toHttpUrl()).create(clazz)
            serviceMap[key] = value
        }
        return value
    }

    /**
     * 创建 OkHttpClient
     */
    private val mOkHttpClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .proxy(Proxy.NO_PROXY)
            .callTimeout(TOME_OUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TOME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TOME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TOME_OUT.toLong(), TimeUnit.SECONDS)
            .cache(provideCache(provideCacheFile())) //设置缓存
            .cookieJar(LocalCookieJar())
            .addNetworkInterceptor(RequestIntercept())
            .build()
    }

    /**
     * 创建 Retrofit
     */
    private fun mRetrofit(url: HttpUrl) = Retrofit.Builder()
        .baseUrl(url) //域名
        .client(mOkHttpClient) //设置okhttp
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //使用rxjava
        .addConverterFactory(ScalarsConverterFactory.create()) // 添加String转换器
        .addConverterFactory(GsonConverterFactory.create()) //使用Gson
        .build()

    /**
     * 设置缓存路径和大小
     */
    private fun provideCache(cacheFile: File) =
        Cache(cacheFile, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE.toLong())

    /**
     * 提供缓存地址
     */
    private fun provideCacheFile() =
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            var file = ActivityUtils.getTopActivity().externalCacheDir //获取系统管理的sd卡缓存文件
            if (file == null) { //如果获取的为空,就是用自己定义的缓存文件夹做缓存路径
                file = File("/mnt/sdcard/${ActivityUtils.getTopActivity().packageName}")
                if (!file.exists()) {
                    file.mkdirs()
                }
            }
            file
        } else {
            ActivityUtils.getTopActivity().cacheDir
        }
}