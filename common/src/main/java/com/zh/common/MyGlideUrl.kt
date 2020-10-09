package com.zh.common

import com.bumptech.glide.load.model.GlideUrl

/**
 * 修改图片缓存方式
 */
class MyGlideUrl(private val mUrl: String) : GlideUrl(mUrl) {
    override fun getCacheKey(): String {
        return if (mUrl.contains("?")) {
            mUrl.substring(0, mUrl.lastIndexOf("?"))
        } else mUrl
    }
}