package com.zh.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.widget.TextView
import com.zh.common.R

/**
 * 验证码控件
 */
@SuppressLint("AppCompatCustomView")
class VCodeView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context!!, attrs, defStyleAttr) {
    private var sendVeriCode: String? = "发送验证码"
    private var mNormalPaint : Paint? = null//画正常文字画笔
    private var mDisablePaint : Paint? = null//画倒计时画笔（不可点击）
    private val normalColor : Int//正常可点击文字颜色
    private val disableColor : Int//不可点击文字颜色
    private var textSizes: Float = 30f //字体大小
    private val defaultCountdownTime = 60 //默认倒计时，60s
    private var countdownTime = defaultCountdownTime //倒计时
    private var isCountdown = false //是否正在倒计时
    private val WHAT_COUNTDOWN = 101
    private val updateTimePeriod = 1000 //更新时间周期，1s
    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                WHAT_COUNTDOWN -> {
                    invalidate()
                    this.removeMessages(WHAT_COUNTDOWN)
                    sendEmptyMessageDelayed(
                        WHAT_COUNTDOWN,
                        updateTimePeriod.toLong()
                    ) //1秒更新一次
                }
            }
        }
    }

    private fun init() {
        mNormalPaint = Paint()
        mNormalPaint?.apply {
            this.textSize = textSizes
            this.isAntiAlias = true
            this.color = normalColor
        }
        mDisablePaint = Paint()
        mDisablePaint?.apply {
            this.textSize = textSizes
            this.isAntiAlias = true
            this.color = disableColor
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //测量控件大小
        setMeasuredDimension(
            getMeasureWH(widthMeasureSpec, 1),
            getMeasureWH(heightMeasureSpec, 2)
        )
    }

    /**
     * 得到控件的尺寸
     *
     * @param widthOrHeight widthMeasureSpec或heightMeasureSpec
     * @param type          表示测量的是宽还是高，1表示宽，2表示高
     * @return
     */
    fun getMeasureWH(widthOrHeight: Int, type: Int): Int {
        val mode = MeasureSpec.getMode(widthOrHeight)
        val size = MeasureSpec.getSize(widthOrHeight)
        when (mode) {
            MeasureSpec.EXACTLY -> return size
            MeasureSpec.AT_MOST -> {
                if (type == 1) { //宽
                    return mNormalPaint!!.measureText(sendVeriCode)
                        .toInt() + paddingLeft + paddingRight
                } else if (type == 2) { //高
                    return (mNormalPaint!!.descent() - mNormalPaint!!.ascent()).toInt() + paddingTop + paddingBottom
                }
                return size
            }
            MeasureSpec.UNSPECIFIED -> return size
        }
        return size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isCountdown) { //正在倒计时
            val text = "" + countdownTime-- + "s"
            val textWidth = mDisablePaint!!.measureText(text)
            val textHeight = mDisablePaint!!.descent() - mDisablePaint!!.ascent()
            canvas.drawText(
                text,
                (measuredWidth - textWidth) / 2,
                (measuredHeight - textHeight) / 2 - mDisablePaint!!.ascent(),
                mDisablePaint!!
            )

            //倒计时完成
            if (countdownTime < 0) {
                isCountdown = false
            }
            this.isEnabled = false
            mHandler.sendEmptyMessageDelayed(WHAT_COUNTDOWN, updateTimePeriod.toLong())
        } else {
            val textWidth = mNormalPaint!!.measureText(sendVeriCode)
            val textHeight = mNormalPaint!!.descent() - mNormalPaint!!.ascent()
            canvas.drawText(
                sendVeriCode!!,
                (measuredWidth - textWidth) / 2,
                (measuredHeight - textHeight) / 2 - mNormalPaint!!.ascent(),
                mNormalPaint!!
            )
            this.isEnabled = true
            mHandler.removeCallbacksAndMessages(null)
        }
    }

    /**
     * 开始倒计时
     *
     * @param time 倒计时的时间
     */
    fun startCountdown(time: Int) {
        isCountdown = true
        countdownTime = if (time > 0) {
            time
        } else {
            defaultCountdownTime
        }
        invalidate()
    }

    /**
     * 停止倒计时
     */
    fun stopCountdown() {
        isCountdown = false
        invalidate()
        mHandler.removeCallbacksAndMessages(null)
    }

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.VCodeView, defStyleAttr, 0)
        normalColor = typedArray.getColor(R.styleable.VCodeView_vc_normalColor, Color.WHITE)
        disableColor = typedArray.getColor(R.styleable.VCodeView_vc_disableColor, Color.WHITE)
        textSizes = typedArray.getDimension(R.styleable.VCodeView_vc_textSize, 30f)
        if (typedArray.hasValue(R.styleable.VCodeView_vc_text)) sendVeriCode =
            typedArray.getString(R.styleable.VCodeView_vc_text)
        init()
    }
}