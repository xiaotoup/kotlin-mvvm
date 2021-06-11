package com.zh.common.utils

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import java.util.*

/**
 * 打开手机地图导航
 */
object OpenMapNavigation {

    /**
     * 高德地图
     *
     * @param lat      纬度
     * @param lng      经度
     */
    fun goGaoDe(lat: String, lng: String, adr: String) {
        if (isAvilible("com.autonavi.minimap")) {
            try {
                val url =
                    "amapuri://route/plan/?sid=BGVIS1&slat=&slon=&sname=&did=&dlat=$lat&dlon=$lng&dname=$adr&dev=0&t=0"
                val intent = Intent("android.intent.action.VIEW", Uri.parse(url))
                ActivityUtils.getTopActivity().startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            ToastUtils.showShort("您尚未安装高德地图")
            val uri = Uri.parse("market://details?id=com.autonavi.minimap")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            ActivityUtils.getTopActivity().startActivity(intent)
        }
    }

    /**
     * 启动腾讯地图App进行导航
     *
     * @param address 目的地
     * @param latStr  必填 纬度
     * @param lonStr  必填 经度
     */
    fun goTenXun(address: String, latStr: String, lonStr: String) {
        if (isAvilible("com.tencent.map")) {
            var lat = 0.0
            var lon = 0.0
            if (!TextUtils.isEmpty(latStr)) {
                lat = latStr.toDouble()
            }
            if (!TextUtils.isEmpty(lonStr)) {
                lon = lonStr.toDouble()
            }
            // 启动路径规划页面
            val naviIntent = Intent(
                "android.intent.action.VIEW",
                Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to=$address&tocoord=$lat,$lon&policy=0&referer=appName")
            )
            ActivityUtils.getTopActivity().startActivity(naviIntent)
        } else {
            ToastUtils.showShort("您尚未安装腾讯地图")
            val uri = Uri.parse("market://details?id=com.tencent.map")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            ActivityUtils.getTopActivity().startActivity(intent)
        }
    }

    /**
     * 启动百度App进行导航
     *
     * @param address 目的地
     * @param latStr  必填 纬度
     * @param lonStr  必填 经度
     */
    fun goToBaidu(address: String, latStr: String, lonStr: String) {
        if (isAvilible("com.baidu.BaiduMap")) {
            var lat = 0.0
            var lon = 0.0
            if (!TextUtils.isEmpty(latStr)) {
                lat = latStr.toDouble()
            }
            if (!TextUtils.isEmpty(lonStr)) {
                lon = lonStr.toDouble()
            }

            //启动路径规划页面
            val url =
                "baidumap://map/direction?origin=我的位置&destination=latlng:$lat,$lon|$address&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"
            val naviIntent = Intent("android.intent.action.VIEW", Uri.parse(url))
            ActivityUtils.getTopActivity().startActivity(naviIntent)
        } else {
            ToastUtils.showShort("您尚未安装百度地图")
            val uri = Uri.parse("market://details?id=com.baidu.BaiduMap")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            ActivityUtils.getTopActivity().startActivity(intent)
        }
    }

    /** 检查手机上是否安装了指定的软件
     * @param packageName：应用包名
     * @return
     */
    private fun isAvilible(packageName: String): Boolean {
        //获取packagemanager
        val packageManager = ActivityUtils.getTopActivity().packageManager
        //获取所有已安装程序的包信息
        val packageInfos = packageManager.getInstalledPackages(0)
        //用于存储所有已安装程序的包名
        val packageNames: MutableList<String> = ArrayList()
        //从pinfo中将包名字逐一取出，压入pName list中
        packageInfos?.also {
            for (i in packageInfos.indices) {
                val packName = packageInfos[i].packageName
                packageNames.add(packName)
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName)
    }
}