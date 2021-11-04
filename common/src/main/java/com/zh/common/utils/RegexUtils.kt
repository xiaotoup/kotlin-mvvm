package com.zh.common.utils

import android.text.InputFilter.LengthFilter
import android.text.TextUtils
import android.widget.EditText
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.regex.Pattern

/**
 * 正则
 */
class RegexUtils

/**
 * 去掉字符串首尾空格，中间空格不做处理
 */
fun String.regexSpace(): String {
    return if (EmptyUtils.isNotEmpty(this)) Pattern.compile("^\\s*|\\s*$").matcher(this)
        .replaceAll("") else ""
}

/**
 * 姓名输入 输入15位数  限制表情符号 不限制空格
 */
fun EditText.nameFormatNoSpace() {
    this.filters = arrayOf(InputNameFilterNoSpace(), LengthFilter(30))
}

/**
 * 保留一位小数限制
 */
fun Double.oneDecimal(): String {
    val df = DecimalFormat("0.0")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this)
}

/**
 * 保留两位小数限制
 */
fun Double.twoDecimal(): String {
    val df = DecimalFormat("0.00")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this)
}

/**
 * 保留整数限制
 */
fun Double.noDecimal(): String {
    val df = DecimalFormat("0")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this)
}

/**
 * 必须 两位小数 或 整数
 */
fun Double.twoOrNoneDecimal(): String {
    val df = DecimalFormat("0.00")
    df.roundingMode = RoundingMode.HALF_UP
    var num: String = df.format(this)
    if (!TextUtils.isEmpty(num) && num.endsWith(".00")) {
        num = num.substring(0, num.length - 3)
    } else if (!TextUtils.isEmpty(num) && num.endsWith(".0")) {
        num = num.substring(0, num.length - 2)
    }
    return num
}