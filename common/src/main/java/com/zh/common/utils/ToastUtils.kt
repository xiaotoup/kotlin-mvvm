package com.zh.common.utils

import android.content.Context
import android.widget.Toast
import java.lang.ref.WeakReference

/**
 * ToastUtils
 */
object ToastUtils {
    private var toast: Toast? = null
    private var mContext: WeakReference<Context>? = null

    fun init(context: Context) {
        mContext = WeakReference(context)
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param msg
     */
    fun showMessageLong(msg: String?) {
        showMessage(msg, Toast.LENGTH_LONG)
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_LONG
     *
     * @param msg
     */
    fun showMessageLong(msg: Int) {
        showMessage(msg, Toast.LENGTH_LONG)
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param msg
     */
    @JvmOverloads
    fun showMessage(msg: Int, len: Int = Toast.LENGTH_SHORT) {
        if (toast == null) {
            toast = Toast.makeText(mContext?.get(),msg.toString() + "",len)
        } else {
            toast?.duration = len
            toast?.setText(msg.toString() + "")
        }
        toast?.show()
    }

    /**
     * Toast发送消息，默认Toast.LENGTH_SHORT
     *
     * @param msg
     */
    @JvmOverloads
    fun showMessage(msg: String?, len: Int = Toast.LENGTH_SHORT) {
        if (toast == null) {
            toast = Toast.makeText(mContext?.get(), msg, len)
        } else {
            toast?.duration = len
            toast?.setText(msg)
        }
        toast?.show()
    }

    /**
     * 关闭当前Toast
     */
    fun cancelCurrentToast() {
        if (toast != null) toast?.cancel()
    }
}