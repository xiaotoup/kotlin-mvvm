package com.zh.config

import android.os.Build

/**
 * 权限管理类
 */
object PermissionConfig {

    /**
     * 存储权限
     */
    val sdcard = listOf(
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE"
    )

    /**
     * 存储权限 与 相机权限
     */
    val sdcard_camera = listOf(
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.CAMERA"
    )

    /**
     * 相机权限
     */
    val camera = listOf(
        "android.permission.CAMERA"
    )

    /**
     * 定位权限、蓝牙权限
     */
    val location = if (Build.VERSION.SDK_INT < 29) {
        //定位权限、蓝牙权限 10以下的系统
        listOf(
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION"
        )
    } else {
        //定位权限、蓝牙权限 10以上的系统
        listOf(
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_BACKGROUND_LOCATION"
        )
    }

    /**
     * 打电话权限
     */
    val callPhone = listOf(
        "android.permission.CALL_PHONE"
    )

    /**
     * 发短信权限
     */
    val sendSms = listOf(
        "android.permission.SEND_SMS"
    )

    /**
     * 通讯录权限
     */
    val contacts = listOf(
        "android.permission.READ_CONTACTS"
    )
}