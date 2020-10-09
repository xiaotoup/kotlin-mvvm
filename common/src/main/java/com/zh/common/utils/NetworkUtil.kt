package com.zh.common.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import retrofit2.HttpException

object NetworkUtil {
    /**
     * Returns true if the Throwable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    fun isHttpStatusCode(throwable: Throwable?, statusCode: Int): Boolean {
        return (throwable is HttpException
                && throwable.code() == statusCode)
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    /**
     * 判断是否是wifi连接
     */
    fun isWifi(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false
        val info = cm.activeNetworkInfo
        if (null != info) {
            if (info.type == ConnectivityManager.TYPE_WIFI) {
                return true
            }
        }
        return false
    }

    /**
     * 打开网络设置界面
     */
    fun openSetting(activity: Activity, requestCode: Int) {
        val intent = Intent("/")
        val cm = ComponentName(
            "com.android.settings",
            "com.android.settings.WirelessSettings"
        )
        intent.component = cm
        intent.action = Intent.ACTION_VIEW
        activity.startActivityForResult(intent, requestCode)
    }
}