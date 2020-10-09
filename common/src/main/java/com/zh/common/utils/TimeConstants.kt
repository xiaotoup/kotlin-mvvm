package com.zh.common.utils

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by lijie on 2018/4/16
 * Description:时间相关常量
 * Email: 731098696@qq.com
 * Version：1.0
 */
object TimeConstants {
    /**
     * 秒与毫秒的倍数
     */
    const val MSEC = 1

    /**
     * 秒与毫秒的倍数
     */
    const val SEC = 1000

    /**
     * 分与毫秒的倍数
     */
    const val MIN = 60000

    /**
     * 时与毫秒的倍数
     */
    const val HOUR = 3600000

    /**
     * 天与毫秒的倍数
     */
    const val DAY = 86400000

    @IntDef(
        MSEC,
        SEC,
        MIN,
        HOUR,
        DAY
    )
    @Retention(RetentionPolicy.SOURCE)
    annotation class Unit
}