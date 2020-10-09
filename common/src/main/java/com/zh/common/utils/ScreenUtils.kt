package com.zh.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.zh.common.base.BaseApplication
import java.lang.ref.WeakReference

/**
 * @author :  zh
 * @description :获得屏幕相关的辅助类
 * @date : 2019/5/6.
 */
class ScreenUtils {
    companion object {
        /**
         * 获取屏幕宽度(不包含底部导航栏)
         */
        val screenWidth: Int
            get() {
                val dm: DisplayMetrics =
                    BaseApplication.getApplication().resources.displayMetrics
                return dm.widthPixels
            }

        /**
         * 获取屏幕高度(不包含底部导航栏)
         */
        val screenHeight: Int
            get() {
                val dm: DisplayMetrics =
                    BaseApplication.getApplication().resources.displayMetrics
                return dm.heightPixels
            }//手机屏幕真实宽度// 可能有虚拟按键的情况

        /**
         * 获取屏幕真实宽度（包含底部导航栏）
         */
        val screenRealWidth: Int
            get() {
                val windowManager = BaseApplication.getApplication()
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = windowManager.defaultDisplay
                val outPoint = Point()
                if (Build.VERSION.SDK_INT >= 19) {
                    // 可能有虚拟按键的情况
                    display.getRealSize(outPoint)
                }
                val mRealSizeWidth: Int //手机屏幕真实宽度
                mRealSizeWidth = outPoint.x
                return mRealSizeWidth
            }//手机屏幕真实高度// 可能有虚拟按键的情况

        /**
         * 获得屏幕真实高度（包含底部导航栏）
         */
        val screenRealHeight: Int
            get() {
                val windowManager = BaseApplication.getApplication()
                    .getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = windowManager.defaultDisplay
                val outPoint = Point()
                if (Build.VERSION.SDK_INT >= 19) {
                    // 可能有虚拟按键的情况
                    display.getRealSize(outPoint)
                }
                val mRealSizeHeight: Int //手机屏幕真实高度
                mRealSizeHeight = outPoint.y
                return mRealSizeHeight
            }

        /**
         * 获取通知栏的高度
         */
        val statusBarHeight: Int
            get() {
                val resources: Resources = BaseApplication.getApplication().resources
                val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                return resources.getDimensionPixelSize(resourceId)
            }

        /**
         * 底部虚拟按键栏的高度
         */
        val navigationBarBarHeight: Int
            get() {
                var resourceId = 0
                val rid: Int = BaseApplication.getApplication().resources
                    .getIdentifier("config_showNavigationBar", "bool", "android")
                return if (rid != 0) {
                    resourceId = BaseApplication.getApplication().resources
                        .getIdentifier("navigation_bar_height", "dimen", "android")
                    BaseApplication.getApplication().resources
                        .getDimensionPixelSize(resourceId)
                } else 0
            }

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dip2px(dpValue: Float): Int {
            val scale: Float =
                BaseApplication.getApplication().resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dip(pxValue: Float): Int {
            val scale: Float =
                BaseApplication.getApplication().resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         */
        fun sp2px(spValue: Float): Int {
            val fontScale: Float =
                BaseApplication.getApplication().resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         */
        fun px2sp(pxValue: Float): Int {
            val fontScale: Float =
                BaseApplication.getApplication().resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 关闭键盘
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun disappearKeyBroad(context: Activity) {
            val imm =
                WeakReference(context).get()
                    ?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (isSoftShowing(
                    WeakReference(
                        context
                    ).get()
                )
            ) { //先判断键盘是否是开启状态，是则关闭
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }

        /**
         * 判断键盘是否在显示
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun isSoftShowing(context: Activity?): Boolean {
            //获取当前屏幕内容的高度
            val screenHeight =
                WeakReference(context).get()!!.window.decorView
                    .height
            //获取View可见区域的bottom
            val rect = Rect()
            WeakReference(context).get()!!.window.decorView
                .getWindowVisibleDisplayFrame(rect)
            return screenHeight - rect.bottom - navigationBarBarHeight != 0
        }
    }
}