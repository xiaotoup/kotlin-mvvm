package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 自定义ViewPager，可动态设置是否支持滑动
 * Created by monty on 2017/8/27.
 */
class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var isCanScroll = true

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (isCanScroll) {
            //允许滑动则应该调用父类的方法
            super.onTouchEvent(ev)
        } else {
            //禁止滑动则不做任何操作，直接返回true即可
            true
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (isCanScroll)
            super.onInterceptTouchEvent(arg0)
        else
            false
    }

    //设置是否允许滑动，true是可以滑动，false是禁止滑动
    fun setIsCanScroll(isCanScroll: Boolean) {
        this.isCanScroll = isCanScroll
    }
}