package com.zh.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.net.URI

/**
 * @description :
 * @author :  zh
 * @date : 2019/5/6.
 */
object ApkUtils {
    /**
     * 获取版本名称
     *
     * @return 当前应用的版本名称
     */
    fun getVersion(context: Context): String {
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            "1.0.0"
        }
    }

    /**
     * 返回当前程序版本号
     */
    fun getAppVersionCode(context: Context): Int {
        var versioncode = 0
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            // versionName = pi.versionName;
            versioncode = pi.versionCode
        } catch (e: Exception) {
            Log.e("VersionInfo", "Exception", e)
        }
        return versioncode
    }

    fun installApk(path: String, context: Context) {
        val i = Intent(Intent.ACTION_VIEW)
        val data: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkName = "tongtonglicai"
            val file = File(URI.create("file://$path"))
            data = FileProvider.getUriForFile(
                context,
                "com.tongtongli.tongtonglihd.fileprovider",
                file
            )
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            i.setDataAndType(data, "application/vnd.android.package-archive")
            context.startActivity(i)
        } else {
            data = Uri.parse("file://$path")
            i.setDataAndType(data, "application/vnd.android.package-archive")
            context.startActivity(i)
        }
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager // 获取packagemanager
        val pinfo =
            packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }
}