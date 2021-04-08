package com.zh.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import com.luck.picture.lib.tools.ScreenUtils

/**
 * 英文换行添加链接符“-”
 */
@SuppressLint("AppCompatCustomView")
class BreakTextView : TextView {
    private var mEnabled = true

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    /**
     * 设置单行展示不下一个单词时是否自动截断
     *
     * @param enable
     */
    fun setAutoSplit(enable: Boolean) {
        mEnabled = enable
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if ( /*getWidth() > 0 && getHeight() > 0 &&*/mEnabled) {
            val newText = autoSplitText(this)
            if (!TextUtils.isEmpty(newText)) {
                text = newText
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 自动折断单词
     * 实现思路：测量TextView宽度时，测量每行Text的宽度，如果一行展示不下某个单词，就将这个单词折断
     *
     * @param tv
     * @return
     */
    private fun autoSplitText(tv: TextView): CharSequence {
        val rawCharSequence = tv.text
        val rawText = rawCharSequence.toString() //原始文本
        val tvPaint: Paint = tv.paint //paint，包含字体等信息
        //        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度
        val tvWidth: Int = ScreenUtils.getScreenWidth(tv.context) / 3 - ScreenUtils.dip2px(tv.context, 10f)

        //将原始文本按行拆分
        val rawTextLines =
            rawText.replace("\r".toRegex(), "").split("\n".toRegex()).toTypedArray()
        val sbNewText = StringBuilder()
        for (rawTextLine in rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine)
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                var lineWidth = 0f
                var cnt = 0
                while (cnt != rawTextLine.length) {
                    val ch = rawTextLine[cnt]
                    lineWidth += tvPaint.measureText(ch.toString())
                    if (lineWidth <= tvWidth - 12) {
                        if (lineWidth >= (tvWidth - tvPaint.measureText((ch.toInt() + 1).toString())
                                    - tvPaint.measureText(ch.toString()))
                            && ch.toString() == "("
                        ) {
                            sbNewText.append("\n")
                            lineWidth = 0f
                            cnt -= 1
                        } else {
                            sbNewText.append(ch)
                        }
                    } else {
                        if (cnt - 2 >= 0 && rawTextLine[cnt - 1] >= 'A' && rawTextLine[cnt - 1] <= 'z' && rawTextLine[cnt - 1] >= 'A' && rawTextLine[cnt - 2] <= 'z'
                        ) {
                            sbNewText.deleteCharAt(sbNewText.length - 1)
                            cnt -= if (ch.toString() == " " || Character.isUpperCase(ch.toInt() - 1)) {
                                sbNewText.append("\n")
                                1
                            } else {
                                sbNewText.append("\n-")
                                2
                            }
                            lineWidth = 0f
                        } else {
                            sbNewText.append("\n")
                            lineWidth = 0f
                            --cnt
                        }
                    }
                    ++cnt
                }
            }
            sbNewText.append("\n")
        }

        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length - 1)
        }
        //使用TextView设置的Span格式
        val sp = SpannableString(sbNewText.toString())
        if (rawCharSequence is Spanned) {
            TextUtils.copySpansFrom(
                rawCharSequence,
                0,
                rawCharSequence.length,
                null,
                sp,
                0
            )
        }
        return sp
    }
}