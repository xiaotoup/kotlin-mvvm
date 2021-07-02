package com.zh.common.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.zh.common.R

/**
 * 自定义通用的左右文字加图片控件
 */
class RelativeItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    /**
     * 控件
     */
    private val mIvLeftImg: ImageView//左边图片
    private val mTvLeft: TextView//左边文字
    private val mEtContent: EditText//输入框文字
    private val mTvRight: TextView//右边文字
    private val mViewDriver: View//底部横线

    /**
     * 左边文字
     */
    private val defaultLeftTextColor = R.color.color_text_333//默认左边文字颜色
    private val defaultLeftTextSize = 14f//默认左边文字大小
    private val defaultLeftTextPaddingLeft = 15f//默认左边文字PaddingLeft
    private val defaultLeftDrawablePadding = 5f//默认左边DrawablePadding

    /**
     * 输入框
     */
    private val defaultEditTextColor = R.color.color_text_333//默认输入框文字颜色
    private val defaultEditTextHintColor = R.color.color_text_999//默认输入框提示文字颜色
    private val defaultEditTextSize = 14f//默认输入框文字大小
    private val defaultEditTextBackgroundColor = Color.WHITE//默认输入框背景色

    /**
     * 右边文字
     */
    private val defaultRightTextColor = R.color.color_text_666//默认右边文字颜色
    private val defaultRightTextSize = 14f//默认右边文字大小
    private val defaultRightTextPaddingRight = 15f//默认右边文字PaddingRight
    private val defaultRightDrawablePadding = 5f//默认右边DrawablePadding

    /**
     * 底部横线
     */
    private val defaultDriverColor = R.color.color_line//默认底部横线颜色
    private val defaultDriverHeight = 0.5f//默认底部横线高度

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_item_relative, this, true)
        mIvLeftImg = findViewById(R.id.ivLeftImg)
        mTvLeft = findViewById(R.id.tvLeft)
        mEtContent = findViewById(R.id.etContent)
        mTvRight = findViewById(R.id.tvRight)
        mViewDriver = findViewById(R.id.viewDriver)

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RelativeItemView, defStyleAttr, 0)

        /**
         * 左边图片
         */
        if (typedArray.hasValue(R.styleable.RelativeItemView_riv_leftImg)) {
            //左边文字PaddingLeft - 默认5dp
            mTvLeft.setPadding(
                SizeUtils.dp2px(5f), 0, 0, 0
            )
            mIvLeftImg.setImageDrawable(typedArray.getDrawable(R.styleable.RelativeItemView_riv_leftImg))
            val lp = mIvLeftImg.layoutParams as LayoutParams
            //左边图片设置宽高
            lp.width = typedArray.getDimension(
                R.styleable.RelativeItemView_riv_leftImgWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT.toFloat()
            ).toInt()
            lp.height = typedArray.getDimension(
                R.styleable.RelativeItemView_riv_leftImgHeight,
                ViewGroup.LayoutParams.WRAP_CONTENT.toFloat()
            ).toInt()
            mIvLeftImg.layoutParams = lp
        }

        /**
         * 左边文字
         */
        mTvLeft.text = typedArray.getString(R.styleable.RelativeItemView_riv_leftText)
        //左边文字颜色
        mTvLeft.setTextColor(
            typedArray.getColor(
                R.styleable.RelativeItemView_riv_leftTextColor,
                defaultLeftTextColor.toInt()
            )
        )
        //左边文字字体大小
        mTvLeft.setTextSize(
            TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(
                R.styleable.RelativeItemView_riv_leftTextSize,
                defaultLeftTextSize
            )
        )
        //左边文字PaddingLeft
        mTvLeft.setPadding(
            typedArray.getDimension(
                R.styleable.RelativeItemView_riv_leftTextPaddingLeft,
                defaultLeftTextPaddingLeft
            ).toInt(), 0, 0, 0
        )
        //左边文字加粗设置
        mTvLeft.typeface = when (typedArray.getInt(
            R.styleable.RelativeItemView_riv_leftTextStyle,
            0
        )) {
            1 -> Typeface.defaultFromStyle(Typeface.BOLD)
            2 -> Typeface.defaultFromStyle(Typeface.ITALIC)
            else -> Typeface.defaultFromStyle(Typeface.NORMAL)
        }
        //左边文字Drawable图片
        if (typedArray.hasValue(R.styleable.RelativeItemView_riv_leftTextDrawable)) {
            mTvLeft.setCompoundDrawablesRelativeWithIntrinsicBounds(
                typedArray.getDrawable(R.styleable.RelativeItemView_riv_leftTextDrawable),
                null,
                null,
                null
            )
            //左边文字Drawable图片Padding
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_leftTextDrawablePadding)) {
                mTvLeft.compoundDrawablePadding = typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_leftTextDrawablePadding,
                    defaultLeftDrawablePadding
                ).toInt()
            }
            //左边文字Drawable图片Tint
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_leftTextDrawableTint)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTvLeft.compoundDrawableTintList = ColorStateList.valueOf(
                        typedArray.getColor(
                            R.styleable.RelativeItemView_riv_leftTextDrawableTint,
                            0
                        )
                    )
                }
            }
        }
        /**
         * 输入框
         */
        if (typedArray.hasValue(R.styleable.RelativeItemView_riv_editText) ||
            typedArray.hasValue(R.styleable.RelativeItemView_riv_editTextHint) ||
            typedArray.hasValue(R.styleable.RelativeItemView_riv_editTextColor) ||
            typedArray.hasValue(R.styleable.RelativeItemView_riv_editTextHintColor) ||
            typedArray.hasValue(R.styleable.RelativeItemView_riv_editTextSize)
        ) {
            mEtContent.visibility = View.VISIBLE
            mEtContent.setText(typedArray.getString(R.styleable.RelativeItemView_riv_editText))
            //输入框文字颜色
            mEtContent.setTextColor(
                typedArray.getColor(
                    R.styleable.RelativeItemView_riv_editTextColor,
                    defaultEditTextColor.toInt()
                )
            )
            //输入框文字字体大小
            mEtContent.textSize = SizeUtils.px2sp(
                typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_editTextSize,
                    defaultEditTextSize
                )
            ).toFloat()
            //输入框文字Hint
            mEtContent.hint = typedArray.getString(R.styleable.RelativeItemView_riv_editTextHint)
            //输入框文字Hint颜色
            mEtContent.setHintTextColor(
                typedArray.getColor(
                    R.styleable.RelativeItemView_riv_editTextHintColor,
                    defaultEditTextHintColor.toInt()
                )
            )
            //输入框文字加粗设置
            mEtContent.typeface = when (typedArray.getInt(
                R.styleable.RelativeItemView_riv_editTextStyle,
                0
            )) {
                1 -> Typeface.defaultFromStyle(Typeface.BOLD)
                2 -> Typeface.defaultFromStyle(Typeface.ITALIC)
                else -> Typeface.defaultFromStyle(Typeface.NORMAL)
            }
            //输入框文字背景
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_editTextBackground)) {
                try {
                    mEtContent.setBackgroundColor(
                        typedArray.getColor(
                            R.styleable.RelativeItemView_riv_editTextBackground,
                            defaultEditTextBackgroundColor
                        )
                    )
                } catch (e: Exception) {
                    mEtContent.background =
                        typedArray.getDrawable(R.styleable.RelativeItemView_riv_editTextBackground)
                    e.printStackTrace()
                }
            }
            //输入框文字是否可编辑
            mEtContent.isEnabled =
                typedArray.getBoolean(R.styleable.RelativeItemView_riv_editTextEnabled, true)
            //输入框文字Gravity位置
            mEtContent.gravity =
                when (typedArray.getInt(R.styleable.RelativeItemView_riv_editTextGravity, 0)) {
                    3 -> Gravity.LEFT.or(Gravity.CENTER_VERTICAL)
                    5 -> Gravity.RIGHT.or(Gravity.CENTER_VERTICAL)
                    else -> Gravity.CENTER_VERTICAL
                }
            //输入框文字MarginLeft
            val marginLeft =
                typedArray.getDimension(R.styleable.RelativeItemView_riv_editTextMarginLeft, 0f)
            //输入框文字MarginRight
            val marginRight =
                typedArray.getDimension(R.styleable.RelativeItemView_riv_editTextMarginRight, 0f)
            val params = mEtContent.layoutParams as LayoutParams
            params.setMargins(marginLeft.toInt(), 0, marginRight.toInt(), 0)
            mEtContent.layoutParams = params
            //输入框文字PaddingLeft
            val paddingLeft =
                typedArray.getDimension(R.styleable.RelativeItemView_riv_editTextPaddingLeft, 0f)
            //输入框文字PaddingRight
            val paddingRight =
                typedArray.getDimension(R.styleable.RelativeItemView_riv_editTextPaddingRight, 0f)
            mEtContent.setPadding(paddingLeft.toInt(), 0, paddingRight.toInt(), 0)
        } else {
            mEtContent.visibility = View.GONE
        }
        /**
         * 右边文字
         */
        mTvRight.text = typedArray.getString(R.styleable.RelativeItemView_riv_rightText)
        //右边文字颜色
        mTvRight.setTextColor(
            typedArray.getColor(
                R.styleable.RelativeItemView_riv_rightTextColor,
                defaultRightTextColor.toInt()
            )
        )
        //右边文字字体大小
        mTvRight.setTextSize(
            TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(
                R.styleable.RelativeItemView_riv_rightTextSize,
                defaultRightTextSize
            )
        )
        //右边文字右边padding
        mTvRight.setPadding(
            0, 0,
            typedArray.getDimension(
                R.styleable.RelativeItemView_riv_rightTextPaddingRight,
                defaultRightTextPaddingRight
            ).toInt(), 0
        )
        //右边文字加粗设置
        mTvRight.typeface = when (typedArray.getInt(
            R.styleable.RelativeItemView_riv_rightTextStyle,
            0
        )) {
            1 -> Typeface.defaultFromStyle(Typeface.BOLD)
            2 -> Typeface.defaultFromStyle(Typeface.ITALIC)
            else -> Typeface.defaultFromStyle(Typeface.NORMAL)
        }
        //右边文字Drawable
        if (typedArray.hasValue(R.styleable.RelativeItemView_riv_rightTextDrawable)) {
            //右边文字Drawable图片
            mTvRight.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                typedArray.getDrawable(R.styleable.RelativeItemView_riv_rightTextDrawable),
                null
            )
            //右边文字Drawable图片Padding
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_rightTextDrawablePadding)) {
                mTvRight.compoundDrawablePadding = typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_rightTextDrawablePadding,
                    defaultRightDrawablePadding
                ).toInt()
            }
            //右边文字Drawable图片Tint
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_rightTextDrawableTint)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTvRight.compoundDrawableTintList = ColorStateList.valueOf(
                        typedArray.getColor(
                            R.styleable.RelativeItemView_riv_rightTextDrawableTint,
                            0
                        )
                    )
                }
            }
        }
        /**
         * 下横线
         */
        if (typedArray.hasValue(R.styleable.RelativeItemView_riv_driverShow)
            && typedArray.getBoolean(R.styleable.RelativeItemView_riv_driverShow, false)
        ) {
            mViewDriver.visibility = View.VISIBLE
            //底部横线颜色
            mViewDriver.setBackgroundColor(
                typedArray.getColor(
                    R.styleable.RelativeItemView_riv_driverColor,
                    defaultDriverColor.toInt()
                )
            )
            val lp = mViewDriver.layoutParams as LayoutParams
            //底部横线高度
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_driverHeight)) {
                lp.height = typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_driverHeight,
                    defaultDriverHeight
                ).toInt()
            } else {
                lp.height = 1
            }
            //底部横线两边距离
            if (typedArray.hasValue(R.styleable.RelativeItemView_riv_driverMarginLeftRight)) {
                val margin = typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_driverMarginLeftRight,
                    0f
                ).toInt()
                lp.leftMargin = margin
                lp.rightMargin = margin
            } else {
                //底部横线左边距离
                lp.leftMargin = typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_driverMarginLeft,
                    0f
                ).toInt()
                //底部横线右边距离
                lp.rightMargin = typedArray.getDimension(
                    R.styleable.RelativeItemView_riv_driverMarginRight,
                    0f
                ).toInt()
            }
            mViewDriver.layoutParams = lp
        }
        typedArray.recycle()
    }

    /**
     * 左边图片
     */
    fun getLeftImgView(): ImageView = mIvLeftImg
    fun setLeftImg(res: Int) {
        mIvLeftImg.setImageResource(res)
    }

    fun setLeftImg(res: Drawable) {
        mIvLeftImg.setImageDrawable(res)
    }

    /**
     * 获取左边文字
     */
    fun getLeftTextView(): TextView = mTvLeft
    fun setLeftTextString(src: Any) {
        mTvLeft.text = "$src"
    }
    fun getLeftString():String = mTvLeft.text.toString()

    /**
     * 获取输入框文字
     */
    fun getEditText(): EditText = mEtContent
    fun setEditTextString(src: Any) {
        mEtContent.setText("$src")
    }

    fun setEditTextHintString(src: Any) {
        mEtContent.hint = "$src"
    }
    fun getEditString():String = mEtContent.text.toString()

    /**
     * 获取右边文字
     */
    fun getRightTextView(): TextView = mTvRight
    fun setRightTextString(src: Any) {
        mTvRight.text = "$src"
    }
    fun getRightString():String = mTvRight.text.toString()
}