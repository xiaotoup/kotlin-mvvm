package com.zh.common

import android.content.Context
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * @Author： zh
 * @Date：2018/5/16 11:56
 * @Des：Glide配置
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context,builder: GlideBuilder) {
        super.applyOptions(context, builder)
        //下面3中设置都可自定义大小，以及完全自定义实现
        //内存缓冲
        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(2f)
            .setBitmapPoolScreens(3f)
            .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))

        //Bitmap 池
        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

        //磁盘缓存
        val diskCacheSizeBytes = 1024 * 1024 * 300 //100 MB
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            builder.setDiskCache(ExternalPreferredCacheDiskCacheFactory(context,diskCacheSizeBytes.toLong()))
        } else {
            builder.setDiskCache(InternalCacheDiskCacheFactory(context,diskCacheSizeBytes.toLong()))
        }
    }
}