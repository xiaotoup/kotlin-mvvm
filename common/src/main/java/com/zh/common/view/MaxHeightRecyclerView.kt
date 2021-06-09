package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.zh.common.R

/**
 * 最大高度 RecyclerView
 */
class MaxHeightRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var maxHeight = 0

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecycler, defStyleAttr, 0)
        maxHeight = typedArray.getDimension(R.styleable.MaxHeightRecycler_maxHeightRecycler, 0f).toInt()
        typedArray.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        var heightSpecNew = heightSpec
        if (maxHeight > 0) {
            heightSpecNew = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthSpec, heightSpecNew)
    }

    /**
     * 设置高度
     */
    fun setMaxHeight(height: Int) {
        maxHeight = height
        invalidate()
    }
}