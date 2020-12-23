package com.zh.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TabWidget

/**
 * @auth xiaohua
 * @time 2020/12/23 - 17:18
 * @desc 小红点 - 角标
 */
open class BadgeView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(mContext, attrs, defStyleAttr) {
    // 该框架内容的文本画笔
    private val mTextPaint: Paint

    // 该控件的背景画笔
    private val mBgPaint: Paint
    private var mHeight = 0
    private var mWidth = 0
    private var mBackgroundShape = SHAPE_CIRCLE
    private var mTextColor = Color.WHITE
    private var mTextSize: Int
    private var mBgColor = Color.RED

    /**
     * @return 控件的字符串文本
     */
    private var badgeText = ""
    private var mGravity = Gravity.RIGHT or Gravity.TOP
    private val mRectF: RectF
    private var mtextH = 0f

    /**
     * @return 改控件的显示状态
     */
    var isShow = false
        private set

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mRectF[0f, 0f, measuredWidth.toFloat()] = measuredHeight.toFloat()
        val fontMetrics = mTextPaint.fontMetrics
        mtextH = fontMetrics.descent - fontMetrics.ascent
        when (mBackgroundShape) {
            SHAPE_CIRCLE -> {
                canvas.drawCircle(
                    measuredWidth / 2f,
                    measuredHeight / 2f, measuredWidth / 2.toFloat(), mBgPaint
                )
                canvas.drawText(
                    badgeText, measuredWidth / 2f, measuredHeight
                            / 2f + (mtextH / 2f - fontMetrics.descent), mTextPaint
                )
            }
            SHAPE_OVAL -> {
                canvas.drawOval(mRectF, mBgPaint)
                canvas.drawText(
                    badgeText, measuredWidth / 2f, measuredHeight
                            / 2f + (mtextH / 2f - fontMetrics.descent), mTextPaint
                )
            }
            SHAPE_RECTANGLE -> {
                canvas.drawRect(mRectF, mBgPaint)
                canvas.drawText(
                    badgeText, measuredWidth / 2f, measuredHeight
                            / 2f + (mtextH / 2f - fontMetrics.descent), mTextPaint
                )
            }
            SHAPE_SQUARE -> {
                val sideLength = measuredHeight.coerceAtMost(measuredWidth)
                mRectF[0f, 0f, sideLength.toFloat()] = sideLength.toFloat()
                canvas.drawRect(mRectF, mBgPaint)
                canvas.drawText(
                    badgeText, sideLength / 2f, sideLength / 2f
                            + (mtextH / 2f - fontMetrics.descent), mTextPaint
                )
            }
            SHAPTE_ROUND_RECTANGLE -> {
                canvas.drawRoundRect(
                    mRectF, dip2px(context, measuredWidth / 2).toFloat(),
                    dip2px(context, measuredWidth / 2).toFloat(), mBgPaint
                )
                canvas.drawText(
                    badgeText, measuredWidth / 2f, measuredHeight
                            / 2f + (mtextH / 2f - fontMetrics.descent), mTextPaint
                )
            }
        }
    }

    /**
     * 设置该控件的背景颜色
     *
     * @param color 背景颜色
     * @return BadgeView
     */
    private fun setBadgeBackgroundColor(color: Int): BadgeView {
        mBgColor = color
        mBgPaint.color = color
        invalidate();
        return this
    }

    /**
     * 设置该控件的背景图形
     *
     * @param shape 图形
     * @return
     */
    private fun setBackgroundShape(shape: Int): BadgeView {
        mBackgroundShape = shape
        invalidate()
        return this
    }

    /**
     * 设置该控件的宽
     *
     * @param width 宽
     * @return BadgeView
     */
    private fun setWidth(width: Int): BadgeView {
        mWidth = width
        this.setBadgeLayoutParams(width, mHeight)
        return this
    }

    /**
     * 设置该控件的高
     *
     * @param height 高
     * @return BadgeView
     */
    private fun setHeight(height: Int): BadgeView {
        mHeight = height
        this.setBadgeLayoutParams(mWidth, height)
        return this
    }

    /**
     * 设置该控件的高和宽
     *
     * @param width  宽
     * @param height 高
     * @return
     */
    private fun setBadgeLayoutParams(width: Int, height: Int): BadgeView {
        mWidth = width
        mHeight = height
        val params = layoutParams as RelativeLayout.LayoutParams
        params.width = dip2px(context, width)
        params.height = dip2px(context, height)
        layoutParams = params
        return this
    }

    /**
     * 设置该控件的位置
     *
     * @param gravity 位置
     * @return BadgeView
     */
    private fun setBadgeGravity(gravity: Int): BadgeView {
        mGravity = gravity
        val params = layoutParams as RelativeLayout.LayoutParams
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layoutParams = params
        return this
    }

    /**
     * 设置该控件的高和宽、位置
     *
     * @param width   宽
     * @param height  高
     * @param gravity 位置
     * @return BadgeView
     */
    private fun setBadgeLayoutParams(width: Int, height: Int, gravity: Int): BadgeView {
        val params = layoutParams as RelativeLayout.LayoutParams
        params.width = dip2px(context, width)
        params.height = dip2px(context, height)
        layoutParams = params
        setBadgeGravity(gravity)
        return this
    }

    /**
     * 设置该控件的文本大小
     *
     * @param size 文本大小（sp）
     * @return
     */
    private fun setTextSize(size: Int): BadgeView {
        mTextSize = sp2px(context, size.toFloat())
        mTextPaint.textSize = sp2px(context, size.toFloat()).toFloat()
        invalidate();
        return this
    }

    /**
     * 设置该控件的文本颜色
     *
     * @param color 文本颜色
     * @return BadgeView
     */
    private fun setTextColor(color: Int): BadgeView {
        mTextColor = color
        mTextPaint.color = color
        invalidate()
        return this
    }

    /**
     * 设置该控件的文本是否为粗体
     *
     * @param flag
     */
    private fun setBadgeBoldText(flag: Boolean) {
        mTextPaint.isFakeBoldText = flag
        invalidate()
    }

    /**
     * 设置该控件要显示的整数文本
     *
     * @param count 要显示的整数文本
     * @return BadgeView
     */
    fun setBadgeText(count: Int): BadgeView {
        badgeText = count.toString()
        if (count == 0) {
            setBadgeLayoutParams(0, 0)
        } else {
            if (badgeText.length == 1) {
                setBadgeLayoutParams(16, 16)
                setBackgroundShape(SHAPE_CIRCLE)
            } else if (badgeText.length >= 2) {
                setBadgeLayoutParams(26, 16)
                setBackgroundShape(SHAPTE_ROUND_RECTANGLE)
                if (count > 99) {
                    badgeText = "99+"
                }
            }
        }
        setTextSize(12)
        setBadgeBackgroundColor(Color.RED)
        invalidate()
        return this
    }

    /**
     * 设置该控件要显示的整数文本数字，超过指定上限显示为指定的上限内容
     *
     * @param count    要显示的整数文本
     * @param maxCount 数字上限
     * @param text     超过上限要显示的字符串文本
     * @return BadgeView
     */
    @Deprecated("")
    private fun setBadgeText(count: Int, maxCount: Int, text: String): BadgeView {
        badgeText = if (count <= maxCount) {
            count.toString()
        } else {
            text
        }
        invalidate()
        return this
    }

    /**
     * 设置该控件要显示的字符串文本
     *
     * @param text 要显示的字符串文本
     * @return BadgeView
     */
    @Deprecated("")
    private fun setBadgeText(text: String): BadgeView {
        badgeText = text
        invalidate()
        return this
    }

    /**
     * 设置绑定的控件
     *
     * @param view 要绑定的控件
     * @return BadgeView
     */
    private fun setBindView(view: View?): BadgeView {
        isShow = true
        if (parent != null) (parent as ViewGroup).removeView(this)
        if (view == null) return this
        when (view.parent) {
            is FrameLayout -> {
                (view.parent as FrameLayout).addView(this)
            }
            is ViewGroup -> {
                val parentContainer = view.parent as ViewGroup
                val viewIndex = (view.parent as ViewGroup).indexOfChild(view)
                (view.parent as ViewGroup).removeView(view)
                val container = FrameLayout(context)
                val containerParams = view.layoutParams
                container.layoutParams = containerParams
                container.id = view.id
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                container.addView(view)
                container.addView(this)
                parentContainer.addView(container, viewIndex)
            }
            null -> {
                Log.e(LOG_TAG, "View must have a parent")
            }
        }
        return this
    }

    /**
     * 设置绑定的控件
     *
     * @param view     要绑定的控件
     * @param tabIndex 要绑定的控件的子项
     */
    private fun setBindView(view: TabWidget, tabIndex: Int) {
        val tabView = view.getChildTabViewAt(tabIndex)
        this.setBindView(tabView)
    }

    /**
     * 移除绑定的控件
     *
     * @return BadgeView
     */
    fun removeBindView(): Boolean {
        if (parent != null) {
            isShow = false
            (parent as ViewGroup).removeView(this)
            return true
        }
        return false
    }

    private fun dip2px(context: Context, dip: Int): Int {
        return (dip * getContext().resources.displayMetrics.density + 0.5f).toInt()
    }

    private fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    companion object {
        protected const val LOG_TAG = "BadgeView"

        // 该控件的背景图形类型
        const val SHAPE_CIRCLE = 1
        const val SHAPE_RECTANGLE = 2
        const val SHAPE_OVAL = 3
        const val SHAPTE_ROUND_RECTANGLE = 4
        const val SHAPE_SQUARE = 5
    }

    init {
        mRectF = RectF()
        mTextSize = dip2px(context, 1)
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.color = mTextColor
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = mTextSize.toFloat()
        mTextPaint.textAlign = Paint.Align.CENTER
        mBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBgPaint.color = mBgColor
        mBgPaint.style = Paint.Style.FILL
        val params = RelativeLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layoutParams = params
    }
}