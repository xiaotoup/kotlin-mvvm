package com.zh.common.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

/**
 * 设置TextView 的drawable属性
 */
object TextViewDrawable {
    private var mContext: WeakReference<Context>? = null
    fun init(context: Context) {
        mContext = WeakReference(context)
    }

    /**
     * DrawableLeft属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    fun setDrawableLeft(textView: TextView, drawableId: Int) {
        val tv =
            SoftReference(textView)
        if (drawableId == 0) {
            tv.get()!!.setCompoundDrawables(null, null, null, null)
        } else {
            val drawable =  ContextCompat.getDrawable(mContext!!.get()!!, drawableId)
            drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.get()!!.setCompoundDrawables(drawable, null, null, null)
        }
    }

    /**
     * DrawableTop属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    fun setDrawableTop(textView: TextView, drawableId: Int) {
        val tv =
            SoftReference(textView)
        if (drawableId == 0) {
            tv.get()!!.setCompoundDrawables(null, null, null, null)
        } else {
            val drawable =
                ContextCompat.getDrawable(mContext!!.get()!!, drawableId)
            drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.get()!!.setCompoundDrawables(null, drawable, null, null)
        }
    }

    /**
     * DrawableRight属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    fun setDrawableRight(textView: TextView, drawableId: Int) {
        val tv =
            SoftReference(textView)
        if (drawableId == 0) {
            tv.get()!!.setCompoundDrawables(null, null, null, null)
        } else {
            val drawable =
                ContextCompat.getDrawable(mContext!!.get()!!, drawableId)
            drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.get()!!.setCompoundDrawables(null, null, drawable, null)
        }
    }

    /**
     * DrawableBottom属性
     *
     * @param textView   控件
     * @param drawableId 资源
     */
    fun setDrawableBottom(textView: TextView, drawableId: Int) {
        val tv =
            SoftReference(textView)
        if (drawableId == 0) {
            tv.get()!!.setCompoundDrawables(null, null, null, null)
        } else {
            val drawable =
                ContextCompat.getDrawable(mContext!!.get()!!, drawableId)
            drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tv.get()!!.setCompoundDrawables(null, null, null, drawable)
        }
    }

    /**
     * Drawable属性的Padding大小
     *
     * @param textView 控件
     * @param padding  大小
     */
    fun setCompoundDrawablePadding(textView: TextView, padding: Int) {
        val tv =
            SoftReference(textView)
        tv.get()!!.compoundDrawablePadding = padding
    }
}