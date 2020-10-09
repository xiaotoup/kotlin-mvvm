package com.zh.common.view.dialog

import android.content.Context
import com.zh.common.R

/**
 * Date: 2018/2/8
 * Time: 11:59
 * Describe: 加载
 */
class LoadingDialog(context: Context) : BaseDialog(context, R.layout.viewstatus_loading) {
    private val handler = MyHandler()
    private val myRunnable = MyRunnable(getContext(), this)

    /**
     * 延时结束
     */
    fun dismissDelayed() {
        if (!isShowing) return
        handler.postDelayed(myRunnable, 100)
    }

    init {
        dialogWindow!!.setDimAmount(0.0f) //设置灰度来达到去除半透明背景
    }
}