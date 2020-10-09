package com.zh.common.utils

import timber.log.Timber

/**
 * @author: zxh tag使用管理
 * @Date: 2019/5/16 0016
 * @Description:
 */
object LogUtil {
    /**
     * 使用默认tag
     * @param msg
     */
    fun v(msg: String) {
        Timber.v(msg)
    }

    /**
     * 自定义tag
     * @param msg
     */
    fun v(tag: String, msg: String) {
        Timber.tag(tag).v(msg)
    }

    /**
     * 使用默认tag
     * @param msg
     */
    fun d(msg: String) {
        Timber.d(msg)
    }

    /**
     * 自定义tag
     * @param msg
     */
    fun d(tag: String, msg: String) {
        Timber.tag(tag).d(msg)
    }

    /**
     * 使用默认tag
     * @param msg
     */
    fun i(msg: String) {
        Timber.i(msg)
    }

    /**
     * 自定义tag
     * @param msg
     */
    fun i(tag: String, msg: String) {
        Timber.tag(tag).i(msg)
    }

    /**
     * 使用默认tag
     * @param msg
     */
    fun w(msg: String) {
        Timber.w(msg)
    }

    /**
     * 自定义tag
     * @param msg
     */
    fun w(tag: String, msg: String) {
        Timber.tag(tag).w(msg)
    }

    /**
     * 使用默认tag
     * @param msg
     */
    fun e(msg: String) {
        Timber.e(msg)
    }

    /**
     * 自定义tag
     * @param msg
     */
    fun e(tag: String, msg: String) {
        Timber.tag(tag).e(msg)
    }
}