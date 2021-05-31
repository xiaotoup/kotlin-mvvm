package com.zh.common.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import java.util.regex.Pattern

/**
 * created by liuyun on 2020/8/26
 */
class InputNameFilterNoSpace : InputFilter {
    private var prompt: String? = null

    //限制表情符号 不限制空格
    private var pattern = Pattern.compile(
        "[^a-zA-Z_.()\\s\\u4E00-\\u9FA5]"
    )

    constructor() {}

    constructor(prompt: String?) {
        this.prompt = prompt
    }

    constructor(prompt: String?, pattern: String?) {
        this.prompt = prompt
        this.pattern = Pattern.compile(pattern)
    }

    override fun filter(
        charSequence: CharSequence, i: Int, i1: Int, spanned: Spanned, i2: Int, i3: Int
    ): CharSequence {
        val matcher = pattern.matcher(charSequence)
        return if (!matcher.find()) {
            ""
        } else {
            if (!TextUtils.isEmpty(prompt)) {
                ToastUtils.showShort(prompt)
            }
            ""
        }
    }
}