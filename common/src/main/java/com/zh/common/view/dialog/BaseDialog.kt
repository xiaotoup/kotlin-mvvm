package com.zh.common.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import com.luck.picture.lib.tools.DoubleUtils
import com.zh.common.R
import io.reactivex.disposables.CompositeDisposable

/**
 * @Author： zxh
 * @Date：2018/5/16 14:21
 * @Des：父Dialog
 */
abstract class BaseDialog : Dialog {
    var dialogWindow: Window? = window
    private var mContext: Context
    private var mCompositeDisposable: CompositeDisposable? = null

    constructor(context: Context, layoutId: Int) : super(context, R.style.StyleDialog) {
        mContext = context
        setContentView(layoutId)
    }

    constructor(context: Context, style: Int, layoutId: Int) : super(context, style) {
        mContext = context
        setContentView(layoutId)
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialogWindow?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        setCanceledOnTouchOutside(true) // 设置点击屏幕Dialog消失
        if (null == mCompositeDisposable) {
            mCompositeDisposable = CompositeDisposable()
        }
        setOnDismissListener {
            mCompositeDisposable?.let { it2 -> it2.clear() }
        }
    }

    fun setDialogWidth(width: Int) {
        val lp = dialogWindow!!.attributes
        lp.width = width
        dialogWindow?.attributes = lp
    }

    fun setDialogHeight(height: Int) {
        val lp = dialogWindow!!.attributes
        lp.height = height
        dialogWindow?.attributes = lp
    }

    fun setDialogParamsMatch() {
        val lp = dialogWindow!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        dialogWindow?.attributes = lp
    }

    fun setBottomAnimation() {
        dialogWindow?.setGravity(Gravity.BOTTOM)
        dialogWindow?.setWindowAnimations(R.style.StyleBottomAnimation)
    }

    fun setAlphaAnimation() {
        dialogWindow?.setWindowAnimations(R.style.StyleAlphaAnimation)
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivity(url: String) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).navigation()
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivity(url: String, bundle: Bundle) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).with(bundle).navigation()
    }

    protected val targetView: View?
        protected get() {
            var ctx: Context? = mContext
            var i = 0
            while (i < 4 && ctx != null && ctx !is Activity && ctx is ContextWrapper) {
                ctx = ctx.baseContext
                i++
            }
            return if (ctx is Activity) {
                ctx.window.decorView
            } else {
                null
            }
        }
}