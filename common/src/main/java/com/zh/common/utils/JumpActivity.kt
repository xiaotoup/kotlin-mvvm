package com.zh.common.utils

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.OnKeyboardListener
import com.luck.picture.lib.tools.DoubleUtils
import com.zh.common.R
import com.zh.config.ZjConfig
import me.jessyan.autosize.internal.CustomAdapt

/**
 * @auth xiaohua
 * @time 2021/4/23 - 14:16
 * @desc 管理跳转页面、标题颜色
 */
interface JumpActivity : CustomAdapt {

    val thisActivity: FragmentActivity?

    //今日头条适配方案
    override fun isBaseOnWidth(): Boolean = true
    override fun getSizeInDp(): Float = ZjConfig.screenWidth

    //可以重写状态栏和导航栏颜色
    // 注：颜色不能使用Color.WHITE设置（报错），必须使用R.color.white
    val statusBarColor: Int get() = defaultStatusBarColor
    val navigationBarColor: Int get() = defaultNavigationBarColor
    val fitsSystemWindows: Boolean get() = true
    val keyboardListener: OnKeyboardListener? get() = null

    //默认状态栏和导航栏颜色
    private val defaultStatusBarColor: Int get() = R.color.white
    private val defaultNavigationBarColor: Int get() = R.color.white

    /**
     * 沉侵式颜色
     */
    private val setStatusBarColor: List<Int>
        get() = listOf(R.color.white)
    private val setNavigationBarColor: List<Int>
        get() = listOf(R.color.white)

    /**
     * 沉侵式
     */
    fun initImmersionBars() {
        thisActivity?.let {
            ImmersionBar.with(it).apply {
                statusBarColor(statusBarColor) //状态栏颜色
                navigationBarColor(navigationBarColor) //导航栏颜色
                //状态栏为淡色statusBarDarkFont要设置为true
                statusBarDarkFont(setStatusBarColor.contains(statusBarColor)) //状态栏字体是深色，不写默认为亮色
                //导航栏为淡色navigationBarDarkIcon要设置为true
                navigationBarDarkIcon(setNavigationBarColor.contains(navigationBarColor)) //导航栏图标是深色，不写默认为亮色
                keyboardEnable(true) //解决软键盘与底部输入框冲突问题，默认为false
                setOnKeyboardListener(keyboardListener)//键盘显示监听
                fitsSystemWindows(fitsSystemWindows)
                init()
            }
        }
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivity(url: String) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).navigation()
    }

    fun startActivity(classActivity: Class<*>) {
        if (DoubleUtils.isFastDoubleClick()) return
        thisActivity?.startActivity(Intent(thisActivity, classActivity))
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivity(url: String, bundle: Bundle) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).with(bundle).navigation()
    }

    fun startActivity(classActivity: Class<*>, bundle: Bundle) {
        if (DoubleUtils.isFastDoubleClick()) return
        thisActivity?.startActivity(Intent(thisActivity, classActivity).putExtras(bundle))
    }

    /**
     * 页面跳转 - 清楚该类之前的所有activity
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivityNewTask(url: String) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun startActivityNewTask(classActivity: Class<*>) {
        if (DoubleUtils.isFastDoubleClick()) return
        thisActivity?.startActivity(
            Intent(thisActivity, classActivity)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    /**
     * 携带数据的页面跳转 - 清楚该类之前的所有activity
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivityNewTask(url: String, bundle: Bundle) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).with(bundle)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun startActivityNewTask(classActivity: Class<*>, bundle: Bundle) {
        if (DoubleUtils.isFastDoubleClick()) return
        thisActivity?.startActivity(
            Intent(thisActivity, classActivity)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtras(bundle)
        )
    }
}