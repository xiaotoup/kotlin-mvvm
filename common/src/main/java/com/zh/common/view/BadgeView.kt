package com.zh.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.zh.common.R

/**
 * 角标 - 小红点
 * 默认最大 99
 */
class BadgeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint: Paint = Paint()
    private var isShowNumber: Boolean//是否显示文字
    private var noTextWidth: Float//没有文字时候的红点宽度
    private var textString: String? = "0"//文字
    private var textColor: Int//文字颜色
    private var textSize: Float//文字大小
    private var backgroundColors: Int//背景色
    private var paddingLeftRight: Float//左右两边的padding距离
    private var paddingTopBottom: Float//上下padding距离
    private var maxNumber: Int//最大数字，默认99

    private var viewWidth = 0f
    private var viewHeight = 0f
    private var textWidth = 0f
    private var textHeight = 0f

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.BadgeView, defStyleAttr, 0)
        isShowNumber = typedArray.getBoolean(R.styleable.BadgeView_bv_show_number, true)
        noTextWidth = typedArray.getDimension(
            R.styleable.BadgeView_bv_no_text_width,
            SizeUtils.dp2px(6f).toFloat()
        )
        textString = typedArray.getString(R.styleable.BadgeView_bv_text)
        textColor = typedArray.getInt(R.styleable.BadgeView_bv_textColor, Color.WHITE)
        textSize = typedArray.getDimension(
            R.styleable.BadgeView_bv_textSize,
            SizeUtils.sp2px(14f).toFloat()
        )
        backgroundColors = typedArray.getInt(R.styleable.BadgeView_bv_backgroundColor, Color.RED)
        paddingLeftRight = typedArray.getDimension(
            R.styleable.BadgeView_bv_padding_left_right,
            SizeUtils.dp2px(5f).toFloat()
        )
        paddingTopBottom = typedArray.getDimension(
            R.styleable.BadgeView_bv_padding_top_bottom,
            SizeUtils.dp2px(3f).toFloat()
        )
        maxNumber = typedArray.getInt(R.styleable.BadgeView_bv_max_number, 99)

        //画笔设置
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = textSize
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.strokeWidth = SizeUtils.dp2px(3f).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //有数字的时候
        if (isShowNumber) {
            textString?.let {
                textWidth = measureText().width().toFloat()
                textHeight = measureText().height().toFloat()
                viewWidth = textWidth + paddingLeftRight * 2
                viewHeight = textHeight + paddingTopBottom * 2
            }
        } else {
            //不显示数据，显示红点的情况
            viewWidth = noTextWidth
            viewHeight = noTextWidth
        }
        setMeasuredDimension(viewWidth.toInt(), viewHeight.toInt())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //当数据为空的时候，不显示任何东西
        if (textString.equals("") || textString.equals("0")) {
            //清空画布
            canvas?.drawColor(Color.TRANSPARENT)
        } else {
            textString?.let {
                if (!isShowNumber) {
                    //不显示文字，只显示红点
                    mPaint.color = backgroundColors
                    canvas?.drawCircle(viewWidth / 2, viewHeight / 2, noTextWidth / 2, mPaint)
                } else {
                    val rectF: RectF
                    //画背景
                    if (it.length == 1) {
                        //画圆
                        mPaint.color = backgroundColors
                        val width = textHeight + paddingTopBottom * 2
                        rectF = RectF(0f, 0f, width, width)
                        val radius = width / 2
                        canvas?.drawCircle(radius, radius, radius, mPaint)
                    } else {
                        //画椭圆
                        mPaint.color = backgroundColors
                        rectF = RectF(0f, 0f, viewWidth, viewHeight)
                        canvas?.drawRoundRect(
                            rectF,
                            SizeUtils.dp2px(25f).toFloat(),
                            SizeUtils.dp2px(25f).toFloat(),
                            mPaint
                        )
                    }
                    canvas?.save()

                    canvas?.restore()
                    // 画文字
                    mPaint.color = textColor
                    val fontMetrics: Paint.FontMetrics = mPaint.fontMetrics
                    val baseLineY = rectF.centerY() - fontMetrics.top / 2 - fontMetrics.bottom / 2
                    canvas?.drawText(it, rectF.centerX(), baseLineY, mPaint)
                    canvas?.save()
                }
            }
        }
    }

    /**
     * 设置数字
     */
    fun setNumber(number: Int) {
        if (textString == null){
            requestLayout()
        } else {
            if (textString != null || number.toString().length == textString!!.length) {
                invalidate()
            } else {
                requestLayout()
            }
        }
        if (number <= 0) {
            textString = "0"
        } else {
            textString = number.toString()
            if (number > maxNumber) {
                textString = "$maxNumber+"
            }
        }
    }

    /**
     * 获取数字
     */
    fun getNumber(): Int {
        textString?.apply {
            if (this.contains("+")) {
                return maxNumber
            }
            return this.toInt()
        }
        return 0
    }

    /**
     * 测量文字宽高
     */
    private fun measureText(): Rect {
        val rect = Rect()
        textString?.let { mPaint.getTextBounds(it, 0, it.length, rect) }
        return rect
    }

    /**
     * 是否显示数字
     */
    fun setShowNumber(showNumber: Boolean) {
        isShowNumber = showNumber
        invalidate()
    }

    /**
     * 设置最大的数字
     */
    fun setMaxNumber(number: Int) {
        maxNumber = number
    }

    /**
     * 设置背景色
     */
    fun setBackgroundColors(backgroundColor: Int) {
        backgroundColors = backgroundColor
    }

    /**
     * 设置文字颜色
     */
    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    /**
     * 设置文字大小
     */
    fun setTextSize(textSize: Float) {
        this.textSize = SizeUtils.sp2px(textSize).toFloat()
    }

    /**
     * 只是红点的宽度大小
     */
    fun setNoNumberWidth(width: Float) {
        noTextWidth = width
        invalidate()
    }
}