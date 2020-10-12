package com.zh.common.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout

/**
 * @auth zxh
 * @des 解决AppBarLayout + RecycleView 滑动后，item 在一段时间内无法点击的问题
 * @time 2020/10/12 - 21:56
 */
class FixAppBarLayoutBehavior : AppBarLayout.Behavior {
    private val TYPE_FLING = 1
    private var mScroller: OverScroller? = null
    private var isFlinging = false
    private var shouldBlockNestedScroll = true

    constructor() : super() {}
    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        getParentScroller(context)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        //如果不是惯性滑动,让他可以执行紧贴操作
        if (!isFlinging) {
            super.onStopNestedScroll(coordinatorLayout, abl, target, type);
        }
        isFlinging = false
        shouldBlockNestedScroll = false
    }

    /**
     * 反射获得滑动属性。
     *
     * @param context
     */
    private fun getParentScroller(context: Context) {
        if (mScroller != null) return
        mScroller = OverScroller(context)
        try {
            //父类AppBarLayout.Behavior  父类的父类   HeaderBehavior
            val reflex_class: Class<*> = javaClass.superclass.superclass
            val fieldScroller = reflex_class.getDeclaredField("mScroller")
            fieldScroller.isAccessible = true
            fieldScroller[this] = mScroller
        } catch (e: Exception) {
        }
    }

    //fling上滑appbar然后迅速fling下滑recycler时, HeaderBehavior的mScroller并未停止, 会导致上下来回晃动
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (mScroller != null) { //当recyclerView 做好滑动准备的时候 直接干掉Appbar的滑动
            if (mScroller!!.computeScrollOffset()) {
                mScroller!!.abortAnimation()
            }
        }

        //决解触摸AppBarLayout滑出屏幕外又回弹回来
        val params: CoordinatorLayout.LayoutParams =
            child.layoutParams as CoordinatorLayout.LayoutParams
        val behavior: AppBarLayout.Behavior = params.behavior as AppBarLayout.Behavior
        behavior!!.setDragCallback(object : DragCallback() {
            override fun canDrag(@NonNull appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })

        if (type == TYPE_FLING) {
            isFlinging = true
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
        stopNestedScrollIfNeeded(dy, child, target, type)
        //type==1时处于非惯性滑动
        if (type == 1) {
            isFlinging = false
        }
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        //惯性滑动的时候设置为true
        isFlinging = true;
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View,
        dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int
    ) {
        if (!shouldBlockNestedScroll)
            super.onNestedScroll(
                coordinatorLayout, child, target, dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, type
            )
        stopNestedScrollIfNeeded(dyUnconsumed, child, target, type)
    }

    private fun stopNestedScrollIfNeeded(
        dy: Int,
        child: AppBarLayout,
        target: View,
        type: Int
    ) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            val currOffset = topAndBottomOffset
            if (dy < 0 && currOffset == 0
                || dy > 0 && currOffset == -child.totalScrollRange
            ) {
                ViewCompat.stopNestedScroll(target, ViewCompat.TYPE_NON_TOUCH)
            }
        }
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        shouldBlockNestedScroll = false
        if (isFlinging) {
            shouldBlockNestedScroll = true
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }
}