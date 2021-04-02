package com.zh.config.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestListener
import com.zh.common.utils.GlideManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @auth xiaohua
 * @time 2020/10/10 - 11:12
 * @desc 图片加载器
 */
class ImageBindingAdapter {}

/**
 * 加载头像 - 不带默认头像
 */
@BindingAdapter("avatarUrl")
fun bindingAvatarAdapter(view: ImageView, avatarUrl: Any) {
    GlideManager.loadAvatarUrl(avatarUrl, view)
}

/**
 * 加载头像 - 带默认头像
 */
@BindingAdapter("avatarUrl", "defaultRes")
fun bindingAvatarAdapter(view: ImageView, avatarUrl: Any, defaultRes: Int = 0) {
    GlideManager.loadAvatarUrl(avatarUrl, view, defaultRes)
}

/**
 * 加载头像 - 带监听
 */
@BindingAdapter("avatarUrl", "listener")
fun bindingAvatarAdapter(view: ImageView, avatarUrl: Any, listener: RequestListener<Drawable>) {
    GlideManager.loadAvatarUrl(avatarUrl, view, listener)
}

/**
 * 加载图片 - 不带默认图
 */
@BindingAdapter("imageUrl")
fun bindingUrlAdapter(view: ImageView, imageUrl: Any) {
    GlideManager.loadUrl(imageUrl, view)
}

/**
 * 加载图片 - 带默认图
 */
@BindingAdapter("imageUrl", "errorRes", "placeRes")
fun bindingUrlAdapter(view: ImageView, imageUrl: Any, errorRes: Int = 0, placeRes: Int = 0) {
    GlideManager.loadUrl(imageUrl, view, errorRes, placeRes)
}

/**
 * 加载图片 - 带监听
 */
@BindingAdapter("imageUrl", "listener")
fun bindingUrlAdapter(view: ImageView, imageUrl: Any, listener: RequestListener<Drawable>) {
    GlideManager.loadUrl(imageUrl, view, listener)
}

/**
 * 加载图片 - 带默认图 - 带监听
 */
@BindingAdapter("imageUrl", "errorRes", "placeRes", "listener")
fun bindingUrlAdapter(
    view: ImageView,
    imageUrl: Any,
    errorRes: Int = 0,
    placeRes: Int = 0,
    listener: RequestListener<Drawable>
) {
    GlideManager.loadUrl(imageUrl, view, errorRes, placeRes, listener)
}

/**
 * 加载gif图片
 */
@BindingAdapter("gifUrl")
fun bindingGifAdapter(view: ImageView, gifUrl: Any) {
    GlideManager.loadGifUrl(gifUrl, view)
}

/**
 * 清除内存缓存
 * 必须在主线程中调用
 */
/*@BindingAdapter("clearMemory")
fun bindingClearMemoryAdapter(view: ImageView) {
    GlobalScope.launch {
        GlideManager.clearMemory()
    }
}*/

/**
 * 清除磁盘缓存
 * 必须在工作线程中调用
 */
//@BindingAdapter("clearDiskCache")
//fun bindingClearDiskCacheAdapter(view: ImageView) {
//    GlideManager.clearDiskCache()
//}
