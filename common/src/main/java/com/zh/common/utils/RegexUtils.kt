package com.zh.common.utils

import android.text.InputFilter.LengthFilter
import android.widget.EditText
import java.util.regex.Pattern

object RegexUtils {
    /**
     * 去掉字符串首尾空格，中间空格不做处理
     */
    fun regexSpace(value: String?): String {
        return Pattern.compile("^\\s*|\\s*$").matcher(value).replaceAll("")
    }

    /**
     * 姓名输入 输入15位数  限制表情符号 不限制空格
     */
    fun setPersonNameFormatNoSpace(moneyET: EditText) {
        moneyET.filters = arrayOf(InputNameFilterNoSpace(), LengthFilter(30))
    }
}