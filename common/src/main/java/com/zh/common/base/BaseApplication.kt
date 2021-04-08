package com.zh.common.base

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Process
import android.text.TextUtils
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zh.common.BuildConfig
import com.zh.common.R
import com.zh.config.ZjConfig
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits
import java.util.*


@SuppressLint("ResourceAsColor")
abstract class BaseApplication : MultiDexApplication() {

    private var mActivityList: LinkedList<RxAppCompatActivity>? = null
    val mBaseUrl: String = ZjConfig.base_url

    //让外部获取到BaseApplication
    companion object {
        private var mApplication: BaseApplication? = null

        @Synchronized
        fun getApplication(): BaseApplication = mApplication!!
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this

        //初始化所有数据
        if (isAppMainProcess()) {
            onCreateMethod()
            //组件化实例化
            initARouter()
            //今日头条适配
            AutoSizeConfig.getInstance()
                .unitsManager
                .setSupportDP(true)
                .setSupportSP(true)
                .supportSubunits = Subunits.MM
        }
    }

    /**
     * 初始化操作
     */
    abstract fun onCreateMethod()

    private fun initARouter() {
        if (BuildConfig.DEBUG) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog() // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    /**
     * 返回一个存储所有存在的activity的列表
     */
    fun getActivityList(): LinkedList<RxAppCompatActivity>? {
        if (mActivityList == null) mActivityList = LinkedList<RxAppCompatActivity>()
        return mActivityList
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    private fun isAppMainProcess(): Boolean {
        return try {
            val pid = Process.myPid()
            val process = getAppNameByPID(applicationContext, pid)
            when {
                TextUtils.isEmpty(process) -> {
                    true
                }
                packageName.equals(process, ignoreCase = true) -> {
                    true
                }
                else -> {
                    false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }

    /**
     * 根据Pid得到进程名
     */
    private fun getAppNameByPID(context: Context, pid: Int): String {
        val manager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == pid) {
                return processInfo.processName
            }
        }
        return ""
    }

    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

    init {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //全局设置主题颜色
            MaterialHeader(context).setColorSchemeColors(R.color.colorPrimary, R.color.colorAccent)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}