package com.zh.common.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import com.blankj.utilcode.util.ActivityUtils

/**
 * PopupWindow基类
 * 默认不显示背景半透明
 */
open class BasePopWindow(
    private val mContext: Context,
    private val showBackgroundAlpha: Boolean = false
) {

    private lateinit var popWindow: PopupWindow
    private lateinit var contentView: View
    private val tag = "myPopupWindow"

    /**
     * 设置 PopupWindow 布局
     */
    open fun createView(view: View): BasePopWindow {
        contentView = view
        setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return this
    }

    /**
     * 设置 PopupWindow 布局
     */
    open fun createView(view: View, width: Int, height: Int): BasePopWindow {
        contentView = view
        setLayout(width, height)
        return this
    }

    /**
     * 设置 PopupWindow 布局
     */
    open fun createView(layoutId: Int): BasePopWindow {
        contentView = LayoutInflater.from(mContext).inflate(layoutId, null, false)
        setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return this
    }

    /**
     * 设置 PopupWindow 布局
     */
    open fun createView(layoutId: Int, width: Int, height: Int): BasePopWindow {
        contentView = LayoutInflater.from(mContext).inflate(layoutId, null, false)
        setLayout(width, height)
        return this
    }

    /**
     * 设置动画
     */
    fun setAnimation(animId: Int): BasePopWindow {
        try {
            popWindow.animationStyle = animId
        } catch (e: Exception) {
            Log.e(tag, "lateinit property popWindow has not been initialized")
            e.printStackTrace()
        }
        return this
    }

    fun showAsDropDown(downView: View): BasePopWindow {
        try {
            popWindow.showAsDropDown(downView)
        } catch (e: Exception) {
            Log.e(tag, "lateinit property popWindow has not been initialized")
            e.printStackTrace()
        }
        return this
    }

    fun showAsDropDown(downView: View, xOff: Int, yOff: Int): BasePopWindow {
        try {
            popWindow.showAsDropDown(downView, xOff, yOff)
        } catch (e: Exception) {
            Log.e(tag, "lateinit property popWindow has not been initialized")
            e.printStackTrace()
        }
        return this
    }

    fun showAsDropDown(downView: View, xOff: Int, yOff: Int, gravity: Int): BasePopWindow {
        try {
            popWindow.showAsDropDown(downView, xOff, yOff, gravity)
        } catch (e: Exception) {
            Log.e(tag, "lateinit property popWindow has not been initialized")
            e.printStackTrace()
        }
        return this
    }

    /**
     * Gravity.TOP | Gravity.LEFT 以屏幕左上角为坐标原点
     * Gravity.BOTTOM | Gravity.RIGHT 以屏幕右下角为坐标原点
     * Gravity.LEFT 以屏幕左侧，屏幕高度 1/2 处为坐标原点
     */
    fun showAtLocation(downView: View, gravity: Int, xOff: Int, yOff: Int): BasePopWindow {
        try {
            popWindow.showAtLocation(downView, gravity, xOff, yOff)
        } catch (e: Exception) {
            Log.e(tag, "lateinit property popWindow has not been initialized")
            e.printStackTrace()
        }
        return this
    }

    /**
     * 背景色设置
     */
    private fun setBackgroundAlpha(bgAlpha: Float) {
        try {
            val lp: WindowManager.LayoutParams = ActivityUtils.getTopActivity().window.attributes
            lp.alpha = bgAlpha
            ActivityUtils.getTopActivity().window.attributes = lp
        } catch (e: Exception) {
            Log.e(tag, "lateinit property contentView has not been initialized")
            e.printStackTrace()
        }
    }

    /**
     * 关闭 PopupWindow
     */
    fun dismissPopWindow() {
        try {
            popWindow.let { if (it.isShowing) it.dismiss() }
        } catch (e: Exception) {
            Log.e(tag, "lateinit property popWindow has not been initialized")
            e.printStackTrace()
        }
    }

    /**
     * 设置 PopupWindow 布局及属性
     */
    private fun setLayout(width: Int, height: Int) {
        popWindow = PopupWindow(contentView, width, height)
        loadData(contentView)
        popWindow.apply {
            setBackgroundDrawable(ColorDrawable())//背景色
            isFocusable = true//解决再次点击该控件无法消失PopWindowBug
            isTouchable = true//设置可以点击
            isOutsideTouchable = true//点击外部消失
            if (showBackgroundAlpha) setBackgroundAlpha(0.6f)
            setOnDismissListener {
                if (showBackgroundAlpha) setBackgroundAlpha(1f)
                setDismissListener()
            }
        }
    }

    open fun loadData(view: View) {}
    open fun setDismissListener() {}
    open fun getPopupWindow(): PopupWindow = popWindow
    open fun isShowing(): Boolean = popWindow.isShowing
}