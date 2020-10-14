package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @auth xiaohua
 * @time 2020/10/14 - 15:33
 * @desc 流布局
 */
class FlowLayout : ViewGroup {
    private var mVerticalSpacing = 0f//每个item纵向间距
    private var mHorizontalSpacing = 0f//每个item横向间距

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    fun setHorizontalSpacing(pixelSize: Float) {
        mHorizontalSpacing = pixelSize
    }

    fun setVerticalSpacing(pixelSize: Float) {
        mVerticalSpacing = pixelSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val selfWidth = View.resolveSize(0, widthMeasureSpec)
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom
        var childLeft = paddingLeft
        var childTop = paddingTop
        var lineHeight = 0

        //通过计算每一个子控件的高度，得到自己的高度
        var i = 0
        val childCount = childCount
        while (i < childCount) {
            val childView = getChildAt(i)
            val childLayoutParams = childView.layoutParams
            childView.measure(
                getChildMeasureSpec(
                    widthMeasureSpec, paddingLeft + paddingRight,
                    childLayoutParams.width
                ),
                getChildMeasureSpec(
                    heightMeasureSpec, paddingTop + paddingBottom,
                    childLayoutParams.height
                )
            )
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight
            lineHeight = Math.max(childHeight, lineHeight)
            if (childLeft + childWidth + paddingRight > selfWidth) {
                childLeft = paddingLeft
                childTop += (childTop + mVerticalSpacing + lineHeight).toInt()
                lineHeight = childHeight
            } else {
                childLeft += childWidth + mHorizontalSpacing.toInt()
            }
            ++i
        }
        val wantedHeight = childTop + lineHeight + paddingBottom
        setMeasuredDimension(
            selfWidth,
            View.resolveSize(wantedHeight, heightMeasureSpec)
        )
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val myWidth = r - l
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        var childLeft = paddingLeft
        var childTop = paddingTop
        var lineHeight = 0

        //根据子控件的宽高，计算子控件应该出现的位置。
        var i = 0
        val childCount = childCount
        while (i < childCount) {
            val childView = getChildAt(i)
            if (childView.visibility === View.GONE) {
                ++i
                continue
            }
            val childWidth = childView.measuredWidth
            val childHeight = childView.measuredHeight
            lineHeight = Math.max(childHeight, lineHeight)
            if (childLeft + childWidth + paddingRight > myWidth) {
                childLeft = paddingLeft
                childTop = (childTop + mVerticalSpacing + lineHeight).toInt()
                lineHeight = childHeight
            }
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
            childLeft += childWidth + mHorizontalSpacing.toInt()
            ++i
        }
    }
}