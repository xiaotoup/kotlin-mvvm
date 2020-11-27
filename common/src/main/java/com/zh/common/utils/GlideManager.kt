package com.zh.common.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.zh.common.MyGlideUrl
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * Created by pc on 2018/2/3.
 * Glide 4.+ 图片显示
 */
object GlideManager {
    private var mContext: WeakReference<Context>? = null

    fun init(context: Context) {
        mContext = WeakReference(context)
    }

    //处理缓存地址
    private fun doUrl(url: Any): Any =
        if (url is String) if (!TextUtils.isEmpty(url) && url.contains("http")) MyGlideUrl(url) else url else url

    /**
     * 头像显示
     *
     * @param url       地址
     * @param imageView 控件
     */
    fun loadAvatarUrl(url: Any, imageView: ImageView) {
        val options = RequestOptions()
            .centerCrop()
            .circleCrop()
            .skipMemoryCache(true)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .into(it1)
        }
    }

    /**
     * 头像显示
     *
     * @param url        地址
     * @param imageView  控件
     * @param defaultRes 默认头像图片
     */
    fun loadAvatarUrl(url: Any, imageView: ImageView, defaultRes: Int) {
        val options = RequestOptions()
            .centerCrop()
            .circleCrop()
            .skipMemoryCache(true)
            .error(defaultRes)
            .placeholder(defaultRes)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .into(it1)
        }
    }

    /**
     * 头像显示
     *
     * @param url             地址
     * @param imageView       控件
     * @param requestListener 状态监听
     */
    fun loadAvatarUrl(url: Any, imageView: ImageView, requestListener: RequestListener<Drawable>) {
        val options = RequestOptions()
            .centerCrop()
            .circleCrop()
            .skipMemoryCache(true)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .listener(requestListener)
                .into(it1)
        }
    }

    /**
     * 加载图片
     *
     * @param url       地址
     * @param imageView 控件
     */
    fun loadUrl(url: Any, imageView: ImageView) {
        val options = RequestOptions()
            .skipMemoryCache(true)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .into(it1)
        }
    }

    /**
     * 加载图片
     *
     * @param url                地址
     * @param imageView          控件
     * @param defaultError       加载错误默认图片
     * @param defaultPlaceholder 预加载图片
     */
    fun loadUrl(url: Any, imageView: ImageView, defaultError: Int, defaultPlaceholder: Int) {
        val options = RequestOptions()
            .skipMemoryCache(true)
            .error(defaultError)
            .placeholder(defaultPlaceholder)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .into(it1)
        }
    }

    /**
     * 加载图片
     *
     * @param url             地址
     * @param imageView       控件
     * @param requestListener 状态监听
     */
    fun loadUrl(url: Any, imageView: ImageView, requestListener: RequestListener<Drawable>) {
        val options = RequestOptions()
            .skipMemoryCache(true)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .listener(requestListener)
                .into(it1)
        }
    }

    /**
     * 加载图片
     *
     * @param url                地址
     * @param imageView          控件
     * @param defaultError       加载错误默认图片
     * @param defaultPlaceholder 预加载图片
     * @param requestListener    状态监听
     */
    fun loadUrl(
        url: Any, imageView: ImageView, defaultError: Int,
        defaultPlaceholder: Int, requestListener: RequestListener<Drawable>
    ) {
        val options = RequestOptions()
            .skipMemoryCache(true)
            .error(defaultError)
            .placeholder(defaultPlaceholder)
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .asDrawable()
                .load(doUrl(url))
                .apply(options)
                .listener(requestListener)
                .into(it1)
        }
    }

    /**
     * 加载gif
     *
     * @param url       地址
     * @param imageView 控件
     */
    fun loadGifUrl(url: Any, imageView: ImageView) {
        val options = RequestOptions()
        SoftReference(imageView).get()?.let { it1 ->
            Glide.with(imageView)
                .load(doUrl(url))
                .apply(options)
                .into(it1)
        }
    }

    /**
     * 清除内存缓存
     * 必须在主线程中调用
     */
    fun clearMemory() {
        mContext?.get()?.let {
            Glide.get(it).clearMemory()
        }
    }

    /**
     * 清除磁盘缓存
     * 必须在工作线程中调用
     */
    fun clearDiskCache() {
        mContext?.get()?.let {
            Glide.get(it).clearDiskCache()
        }
    }
}