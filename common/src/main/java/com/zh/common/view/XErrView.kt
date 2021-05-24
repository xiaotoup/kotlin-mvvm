package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.zh.common.R
import com.zh.common.view.listener.OnRefreshClickListener
import kotlinx.android.synthetic.main.viewstatus_loading_faile.view.*
import kotlinx.android.synthetic.main.viewstatus_loading_faile.view.iv_img
import kotlinx.android.synthetic.main.viewstatus_loading_faile.view.tv_content
import kotlinx.android.synthetic.main.viewstatus_no_data.view.*

/**
 * 自定义错误页面
 */
class XErrView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val view = inflate(context, R.layout.viewstatus_loading_faile, this)
    private val tvRefresh = view.tvRefresh
    private var mOnRefreshClickListener: OnRefreshClickListener? = null

    fun setOnRefreshClickListener(listener: OnRefreshClickListener) {
        mOnRefreshClickListener = listener
    }

    fun setTextDesc(desc: String) {
        view.tv_content.text = desc
    }

    fun setTextSize(size: Float) {
        view.tv_content.textSize = SizeUtils.px2sp(size).toFloat()
    }

    fun setTextColor(color: Int) {
        view.tv_content.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setImage(resId: Int) {
        view.iv_img.setImageResource(resId)
    }

    fun setImageSize(width: Int, height: Int) {
        view.iv_img.layoutParams.width = width
        view.iv_img.layoutParams.height = height
    }

    init {
        tvRefresh.setOnClickListener { mOnRefreshClickListener?.onRefreshClick() }
    }
}