package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.zh.common.R
import com.zh.common.view.listener.OnRefreshClickListener
import kotlinx.android.synthetic.main.viewstatus_loading_faile.view.*

class XErrView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val view = inflate(context, R.layout.viewstatus_loading_faile, this)
    private val tvRefresh = view.tvRefresh
    private var mOnRefreshClickListener: OnRefreshClickListener? = null

    fun setOnRefreshClickListener(listener: OnRefreshClickListener) {
        mOnRefreshClickListener = listener
    }

    init {
        tvRefresh.setOnClickListener { mOnRefreshClickListener?.onRefreshClick() }
    }
}