package com.zh.common.view

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.SizeUtils
import com.zh.common.R

/**
 * 加载菊花
 */
class QMUILoadingView : View {

    private val LINE_COUNT = 12
    private val DEGREE_PER_LINE = 360 / LINE_COUNT
    private var mSize: Int
    private var mPaintColor: Int
    private var mAnimateValue = 0
    private var mAnimator: ValueAnimator? = null
    private var mPaint: Paint? = null

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val array =
            getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0)
        mSize = array.getDimensionPixelSize(
            R.styleable.LoadingView_loading_view_size,
            SizeUtils.dp2px(45f)
        )
        mPaintColor = array.getInt(R.styleable.LoadingView_loading_view_color, Color.WHITE)
        array.recycle()
        initPaint()
    }

    constructor(context: Context?, size: Int, color: Int) : super(context) {
        mSize = size
        mPaintColor = color
        initPaint()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint?.apply {
            color = mPaintColor
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
        }
    }

    fun setColor(color: Int) {
        mPaintColor = color
        mPaint?.color = color
        invalidate()
    }

    fun setSize(size: Int) {
        mSize = size
        requestLayout()
    }

    private val mUpdateListener = AnimatorUpdateListener { animation ->
        mAnimateValue = animation.animatedValue as Int
        invalidate()
    }

    fun start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1)
            mAnimator?.apply {
                addUpdateListener(mUpdateListener)
                duration = 600
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                start()
            }
        } else if (!mAnimator!!.isStarted) {
            mAnimator?.start()
        }
    }

    fun stop() {
        mAnimator?.apply {
            clearAnimation()
            removeUpdateListener(mUpdateListener)
            removeAllUpdateListeners()
            cancel()
            mAnimator = null
        }
    }

    private fun drawLoading(canvas: Canvas, rotateDegrees: Int) {
        val width = mSize / 12
        val height = mSize / 6
        mPaint?.apply {
            this.strokeWidth = width.toFloat()
            canvas.rotate(rotateDegrees.toFloat(), (mSize / 2).toFloat(), (mSize / 2).toFloat())
            canvas.translate((mSize / 2).toFloat(), (mSize / 2).toFloat())
            for (i in 0 until LINE_COUNT) {
                canvas.rotate(DEGREE_PER_LINE.toFloat())
                this.alpha = (255f * (i + 1) / LINE_COUNT).toInt()
                canvas.translate(0f, (-mSize / 2 + width / 2).toFloat())
                canvas.drawLine(0f, 0f, 0f, height.toFloat(), this)
                canvas.translate(0f, (mSize / 2 - width / 2).toFloat())
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mSize, mSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val saveCount =
            canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)
        drawLoading(canvas, mAnimateValue * DEGREE_PER_LINE)
        canvas.restoreToCount(saveCount)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            start()
        } else {
            stop()
        }
    }
}