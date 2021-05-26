package com.zh.common.view

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.luck.picture.lib.tools.DoubleUtils
import com.zh.common.R

/**
 * 自定义标题控件
 */
class TitleBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle) {
    private var mLeftImage: ImageView? = null
    private var mRightImage: ImageView? = null
    private var mRightImage2: ImageView? = null
    private var mCenterTextView: TextView? = null
    private var mRightTextView: TextView? = null
    private var mLeftTextView: TextView? = null
    private var mTitleBar: RelativeLayout? = null
    private var mDividerView: View? = null//下划线
    private var mCenterString: String? = null// 标题文字
    private var mCenterColor = Color.WHITE // 标题颜色
    private var mCenterTextSize: Float? = null // 标题文字大小 = 0f
    private var mRightString: String? = null//右边文字
    private var mRightColor = Color.WHITE // 右边文字颜色
    private var mRightTextSize: Float? = null // 右边文字大小 = 0f
    private var mLeftString: String? = null//左边文字
    private var mLeftColor = Color.WHITE // 左边文字颜色
    private var mLeftTextSize: Float? = null // 左边文字大小 = 0f
    private var isShowDivider: Boolean? = null //是否显示分界线 = false
    private var mTitleBarBackground = Color.GREEN //标题栏背景色
    private var mTitleBarHeight: Float? = null //标题栏高度 = 0f
    private var mLeftDrawable: Drawable? = null//左边图片
    private var mRightDrawable: Drawable? = null//右边图片
    private var mRightDrawable2: Drawable? = null//右边图片2

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) {
        LayoutInflater.from(context).inflate(R.layout.title_bar_layout, this, true)
        mTitleBar = findViewById<View>(R.id.rl_title_bar_layout) as RelativeLayout
        mLeftImage = findViewById<View>(R.id.title_bar_left_image) as ImageView
        mRightImage = findViewById<View>(R.id.title_bar_right_image) as ImageView
        mRightImage2 = findViewById<View>(R.id.title_bar_right_image2) as ImageView
        mLeftTextView = findViewById<View>(R.id.title_bar_left_text_view) as TextView
        mRightTextView = findViewById<View>(R.id.title_bar_right_text_view) as TextView
        mCenterTextView = findViewById<View>(R.id.title_bar_center_text) as TextView
        mDividerView = findViewById(R.id.title_bar_divider)

        // Load attributes
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.TitleBarView, defStyle, 0)

        // 标题文字属性
        mCenterString = typedArray.getString(R.styleable.TitleBarView_tb_centerText)
        mCenterColor =
            typedArray.getColor(R.styleable.TitleBarView_tb_centerTextColor, mCenterColor)
        mCenterTextSize = typedArray.getDimension(R.styleable.TitleBarView_tb_centerTextSize, 0f)

        //右边文字属性
        mRightString = typedArray.getString(R.styleable.TitleBarView_tb_rightText)
        mRightColor = typedArray.getColor(R.styleable.TitleBarView_tb_rightTextColor, mRightColor)
        mRightTextSize = typedArray.getDimension(R.styleable.TitleBarView_tb_rightTextSize, 0f)

        //左边文字属性
        mLeftString = typedArray.getString(R.styleable.TitleBarView_tb_leftText)
        mLeftColor = typedArray.getColor(R.styleable.TitleBarView_tb_leftTextColor, mLeftColor)
        mLeftTextSize = typedArray.getDimension(R.styleable.TitleBarView_tb_leftTextSize, 0f)

        //分界线
        isShowDivider = typedArray.getBoolean(R.styleable.TitleBarView_tb_divider, false)
        mDividerView?.visibility = if (isShowDivider as Boolean) View.VISIBLE else View.GONE

        //TitleBar属性
        mTitleBarBackground =
            typedArray.getColor(R.styleable.TitleBarView_tb_titleBarBackground, mTitleBarBackground)
        mTitleBarHeight = typedArray.getDimension(R.styleable.TitleBarView_tb_titleBarHeight, 0f)

        //左边图片是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftImageDrawable)) {
            mLeftDrawable = typedArray.getDrawable(R.styleable.TitleBarView_tb_leftImageDrawable)
            mLeftImage?.apply {
                this.setImageDrawable(mLeftDrawable)
                this.visibility = View.VISIBLE
                this.setOnClickListener {
                    if (DoubleUtils.isFastDoubleClick()) return@setOnClickListener
                    (getContext() as Activity).finish()
                }
            }
        } else {
            mLeftImage?.visibility = View.GONE
        }

        //右边图片是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightImageDrawable)) {
            mRightDrawable = typedArray.getDrawable(R.styleable.TitleBarView_tb_rightImageDrawable)
            mRightImage?.apply {
                this.setImageDrawable(mRightDrawable)
                this.visibility = View.VISIBLE
            }
        } else {
            mRightImage?.visibility = View.GONE
        }

        //右边图片是否可见2
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightImageDrawable2)) {
            mRightDrawable2 =
                typedArray.getDrawable(R.styleable.TitleBarView_tb_rightImageDrawable2)
            mRightImage2?.apply {
                this.setImageDrawable(mRightDrawable2)
                this.visibility = View.VISIBLE
            }
        } else {
            mRightImage2?.visibility = View.GONE
        }

        //右边图片距离右边距离
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightImage2_marginRight)) {
            setRightImage2MarginRight(
                typedArray.getDimension(
                    R.styleable.TitleBarView_tb_rightImage2_marginRight,
                    0f
                )
            )
        }

        //右边文字是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightText)) {
            mRightTextView?.apply {
                this.text = mRightString
                this.visibility = View.VISIBLE
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightTextColor)) {
                setRightTextColor(mRightColor)
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_rightTextSize)) {
                mRightTextSize?.let { setRightTextSize(it) }
            }
        } else {
            mRightTextView?.visibility = View.GONE
        }

        //左边文字是否可见
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftText)) {
            mLeftTextView?.apply {
                this.text = mLeftString
                this.visibility = View.VISIBLE
                this.setOnClickListener {
                    if (DoubleUtils.isFastDoubleClick()) return@setOnClickListener
                    (getContext() as Activity).finish()
                }
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftTextColor)) {
                setLeftTextColor(mLeftColor)
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_leftTextSize)) {
                mLeftTextSize?.let { setLeftTextSize(it) }
            }
        } else {
            mLeftTextView?.visibility = View.GONE
        }

        //设置标题文字
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_centerText)) {
            setCenterText(mCenterString)
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_centerTextColor)) {
                setCenterColor(mCenterColor)
            }
            if (typedArray.hasValue(R.styleable.TitleBarView_tb_centerTextSize)) {
                mCenterTextSize?.let { setCenterTextSize(it) }
            }
        }

        //设置TitleBar
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_titleBarHeight)) {
            mTitleBarHeight?.let { setTitleBarHeight(it) }
        }
        if (typedArray.hasValue(R.styleable.TitleBarView_tb_titleBarBackground)) {
            setTitleBarBackground(mTitleBarBackground)
        }
        typedArray.recycle()
    }

    /**
     * 左边图片点击事件
     *
     * @param listener
     */
    fun setLeftImageClickListener(listener: OnClickListener?) {
        if (mLeftImage != null) {
            mLeftImage?.setOnClickListener(listener)
        }
    }

    /**
     * 右边图片点击事件
     *
     * @param listener
     */
    fun setRightImageClickListener(listener: OnClickListener?) {
        if (mRightImage != null) {
            mRightImage?.setOnClickListener(listener)
        }
    }

    /**
     * 右边图片点击事件2
     *
     * @param listener
     */
    fun setRightImage2ClickListener(listener: OnClickListener?) {
        if (mRightImage2 != null) {
            mRightImage2?.setOnClickListener(listener)
        }
    }

    /**
     * 右边图片2_距离右边距离
     *
     * @param right
     */
    fun setRightImage2MarginRight(right: Float) {
        if (mRightImage2 != null) {
            (mRightImage2?.layoutParams as LayoutParams).rightMargin =
                right.toInt()
        }
    }

    /**
     * 右边文件点击事件
     *
     * @param listener
     */
    fun setRightTextClickListener(listener: OnClickListener?) {
        if (mRightTextView != null) {
            mRightTextView?.setOnClickListener(listener)
        }
    }

    /**
     * 左边文字点击事件
     *
     * @param listener
     */
    fun setLeftTextClickListener(listener: OnClickListener?) {
        if (mLeftTextView != null) {
            mLeftTextView?.setOnClickListener(listener)
        }
    }

    /**
     * 设置左边文字是否可见
     *
     * @param visibility
     */
    fun setLeftTextVisibility(visibility: Int) {
        if (mLeftTextView != null) {
            mLeftTextView?.visibility = visibility
        }
    }

    /**
     * 设置左边文字
     *
     * @param text
     */
    fun setLeftText(text: String?) {
        if (mLeftTextView != null) {
            mLeftTextView?.text = text
        }
    }

    /**
     * 设置左边文字颜色
     *
     * @param color
     */
    fun setLeftTextColor(color: Int) {
        if (mLeftTextView != null) {
            mLeftTextView?.setTextColor(color)
        }
    }

    /**
     * 设置左边文字大小
     *
     * @param size
     */
    fun setLeftTextSize(size: Float) {
        if (mLeftTextView != null) {
            mLeftTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    fun setRightText(text: String?) {
        if (mRightTextView != null) {
            mRightTextView?.text = text
        }
    }

    /**
     * 设置右边文字颜色
     *
     * @param color
     */
    fun setRightTextColor(color: Int) {
        if (mRightTextView != null) {
            mRightTextView?.setTextColor(color)
        }
    }

    /**
     * 设置右边文字大小
     *
     * @param size
     */
    fun setRightTextSize(size: Float) {
        if (mRightTextView != null) {
            mRightTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }

    /**
     * 设置右边文字是否可见
     *
     * @param visibility
     */
    fun setRightTextVisibility(visibility: Int) {
        if (mRightTextView != null) {
            mRightTextView?.visibility = visibility
        }
    }

    /**
     * 设置右边的图片是否可见
     *
     * @param visibility
     */
    fun setRightImageVisibility(visibility: Int) {
        if (mRightImage != null) {
            mRightImage?.visibility = visibility
        }
    }

    /**
     * 设置右边的图片是否可见2
     *
     * @param visibility
     */
    fun setRightImage2Visibility(visibility: Int) {
        if (mRightImage2 != null) {
            mRightImage2?.visibility = visibility
        }
    }

    /**
     * 设置右边图片
     *
     * @param resource
     */
    fun setRightImageResource(resource: Int) {
        if (mRightImage != null) {
            mRightImage?.setImageResource(resource)
        }
    }

    /**
     * 设置右边图片2
     *
     * @param resource
     */
    fun setRightImage2Resource(resource: Int) {
        if (mRightImage2 != null) {
            mRightImage2?.setImageResource(resource)
        }
    }

    /**
     * 设置左边的图片是否可见
     *
     * @param visibility
     */
    fun setLeftImageVisibility(visibility: Int) {
        if (mLeftImage != null) {
            mLeftImage?.visibility = visibility
        }
    }

    /**
     * 设置左边图片
     *
     * @param resource
     */
    fun setLeftImageResource(resource: Int) {
        if (mLeftImage != null) {
            mLeftImage?.setImageResource(resource)
        }
    }

    /**
     * 设置中间的标题文字
     *
     * @param text
     */
    fun setCenterText(text: String?) {
        if (mCenterTextView != null) {
            mCenterTextView?.text = text
        }
    }

    /**
     * 设置中间的标题文字颜色
     *
     * @param color
     */
    fun setCenterColor(color: Int) {
        if (mCenterTextView != null) {
            mCenterTextView?.setTextColor(color)
        }
    }

    /**
     * 设置中间的标题文字大小
     *
     * @param size
     */
    fun setCenterTextSize(size: Float) {
        if (mCenterTextView != null) {
            mCenterTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }
    }

    /**
     * 设置中间的标题文字是否可见
     *
     * @param visibility
     */
    fun setCenterTextVisibility(visibility: Int) {
        if (mCenterTextView != null) {
            mCenterTextView?.visibility = visibility
        }
    }

    /**
     * 设置分割线是否可见
     *
     * @param visibility
     */
    fun setDividerVisibility(visibility: Int) {
        if (mDividerView != null) {
            mDividerView?.visibility = visibility
        }
    }

    /**
     * 设置TitleBar背景色
     *
     * @param color
     */
    fun setTitleBarBackground(color: Int) {
        if (mTitleBar != null) {
            mTitleBar?.setBackgroundColor(color)
        }
    }

    /**
     * 设置TitleBar高度
     *
     * @param height
     */
    fun setTitleBarHeight(height: Float) {
        if (mTitleBar != null) {
            mTitleBar?.layoutParams?.height = height.toInt()
        }
    }

    init {
        init(context, attrs, defStyle)
    }
}