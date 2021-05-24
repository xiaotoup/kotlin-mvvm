package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.zh.common.R
import kotlinx.android.synthetic.main.viewstatus_no_data.view.*
/**
 * 自定义Loading页面
 */
class XLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val view = inflate(context, R.layout.viewstatus_loading, this)

    fun setTextDesc(desc: String) {
        view.tv_content.text = desc
    }

    fun setTextSize(size: Float) {
        view.tv_content.textSize = SizeUtils.px2sp(size).toFloat()
    }

    fun setTextColor(color: Int) {
        view.tv_content.setTextColor(ContextCompat.getColor(context, color))
    }
}