package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.zh.common.R

class XLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val view = inflate(context, R.layout.viewstatus_loading, this)
}