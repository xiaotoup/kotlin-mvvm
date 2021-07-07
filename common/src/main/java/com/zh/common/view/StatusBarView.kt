package com.zh.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.BarUtils
import com.zh.common.R

/**
 * 自定义状态栏，可自定义颜色
 */
class StatusBarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()
    private val defaultColor = R.color.white//默认颜色

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.StatusBarView, defStyleAttr, 0)
        if (typedArray.hasValue(R.styleable.StatusBarView_sv_backgroundColor)) {
            paint.color = typedArray.getColor(
                R.styleable.StatusBarView_sv_backgroundColor,
                defaultColor.toInt()
            )
        } else {
            paint.color = ContextCompat.getColor(context, defaultColor)
        }
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //动态获取状态栏高度，并设置
        setMeasuredDimension(widthMeasureSpec, BarUtils.getStatusBarHeight())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), paint)
    }
}