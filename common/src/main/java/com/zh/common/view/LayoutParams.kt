package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams

class LayoutParams : MarginLayoutParams {
    constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs) {}
    constructor(width: Int, height: Int) : super(width, height) {}
    constructor(source: MarginLayoutParams?) : super(source) {}
    constructor(source: ViewGroup.LayoutParams?) : super(source) {}
}