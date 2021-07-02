package com.zh.kotlin_mvvm.service

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings

object OpenAccessibilitySettingHelper {

    //跳转到设置页面无障碍服务开启自定义辅助功能服务
    fun jumpToSettingPage(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    //判断自定义辅助功能服务是否开启
    fun isAccessibilitySettingsOn(context: Context, className: String): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningServices = activityManager.getRunningServices(100)// 获取正在运行的服务列表
        if (runningServices.size < 0) {
            return false
        }
        runningServices.forEachIndexed { i, _ ->
            val service: ComponentName = runningServices[i].service;
            if (service.className == className) {
                return true
            }
        }
        return false
    }
}