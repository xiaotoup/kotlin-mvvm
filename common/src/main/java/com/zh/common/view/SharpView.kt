package com.zh.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.zh.common.R

/**
 * 绘制三角形
 */
class SharpView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint() //画三角的画笔
    private var color: Int = Color.WHITE //三角的颜色
    private var width = 10f //三角的宽度
    private var height = 8f //三角的高度

    private var svTOP = 0
    private var svBOTTOM = 1
    private var svRIGHT = 2
    private var svLEFT = 3
    private var direction = svTOP //三角的方向

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SharpView, defStyleAttr, 0)
        color = typedArray.getColor(R.styleable.SharpView_sv_triangle_color, color)
        direction = typedArray.getInt(R.styleable.SharpView_sv_direction, direction)
        width = typedArray.getDimension(R.styleable.SharpView_sv_resolutionWidth, width)
        height = typedArray.getDimension(R.styleable.SharpView_sv_resolutionHeight, height)
        typedArray.recycle()

        //设置画笔的颜色
        mPaint.color = color
        //设置画笔抗锯齿
        mPaint.isAntiAlias = true
        //设置画笔为实心的
        mPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width.toInt(), height.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val sWidth = width.toInt()
        val sHeight = height.toInt()
        //设置画笔的路径
        val path = Path()
        when (direction) {
            svTOP -> {
                path.moveTo(0f, sHeight.toFloat())
                path.lineTo(sWidth.toFloat(), sHeight.toFloat())
                path.lineTo(sWidth.toFloat() / 2, 0f)
            }
            svBOTTOM -> {
                path.moveTo(0f, 0f)
                path.lineTo(sWidth.toFloat() / 2, sHeight.toFloat())
                path.lineTo(sWidth.toFloat(), 0f)
            }
            svLEFT -> {
                path.moveTo(0f, sHeight.toFloat() / 2)
                path.lineTo(sWidth.toFloat(), sHeight.toFloat())
                path.lineTo(sWidth.toFloat(), 0f)
            }
            svRIGHT -> {
                path.moveTo(0f, 0f)
                path.lineTo(0f, sHeight.toFloat())
                path.lineTo(sWidth.toFloat(), sHeight.toFloat() / 2)
            }
        }
        path.close()
        canvas?.drawPath(path, mPaint)
    }
}