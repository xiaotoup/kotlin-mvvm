package com.zh.common.view.dialog

import android.content.Context
import android.view.View
import com.zh.common.R
import kotlinx.android.synthetic.main.dialog_loading.*


/**
 * Date: 2018/2/8
 * Time: 11:59
 * Describe: 加载Loading动画
 */
class LoadingDialog(context: Context) :
    BaseDialog(context, R.style.StyleDialogDimEnabled, R.layout.dialog_loading) {

    private val handler = MyHandler()
    private val myRunnable = MyRunnable(getContext(), this)

    init {
        setCancelable(true)
        setCanceledOnTouchOutside(false)
    }

    /**
     * 加载成功
     */
    fun loadSuccess() {
        loading_img.visibility = View.VISIBLE
        loading_progress.visibility = View.GONE
        loading_img.setImageResource(R.drawable.load_success)
        tv_content.text = "加载成功"
        dismissDelayed()
    }

    /**
     * 加载失败
     */
    fun loadFail() {
        loading_img.visibility = View.VISIBLE
        loading_progress.visibility = View.GONE
        loading_img.setImageResource(R.drawable.load_fail)
        tv_content.text = "加载失败"
        dismissDelayed()
    }

    /**
     * 延时结束
     */
    private fun dismissDelayed() {
        if (!isShowing) return
        handler.postDelayed(myRunnable, 1000)
    }
}