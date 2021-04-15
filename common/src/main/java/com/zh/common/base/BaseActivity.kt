package com.zh.common.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.OnKeyboardListener
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zh.common.R
import com.zh.common.base.factory.ViewModelFactory
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.utils.LanguageUtil
import com.zh.config.ZjConfig
import me.jessyan.autosize.internal.CustomAdapt


/**
 * @auth xiaohua
 * @time 2020/10/7 - 15:21
 * @desc Activity基类，MVVM架构
 */
abstract class BaseActivity<BINDING : ViewDataBinding, VM : BaseViewModel<*>> :
    RxAppCompatActivity(), CustomAdapt {

    lateinit var binding: BINDING
    var mViewModel: VM? = null
    private var viewModelId = 0
    private val isNotAddActivityList = "is_add_activity_list" //是否加入到activity的list，管理
    private var mApplication: BaseApplication? = null

    //默认状态栏和导航栏颜色
    private val defaultStatusBarColor = R.color.white
    private val defaultNavigationBarColor = R.color.white

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApplication = application as BaseApplication
        //添加到栈管理
        val isNotAdd = intent.getBooleanExtra(isNotAddActivityList, false)
        synchronized(BaseActivity::class.java) {
            if (!isNotAdd) mApplication?.getActivityList()?.add(this)
        }
        initViewDataBinding()
        initImmersionBar()
        //初始化组件
        ARouter.getInstance().inject(this)
        initView(savedInstanceState)
        initData()
    }

    private fun initViewDataBinding() {
        if (layoutRes != 0) binding = DataBindingUtil.setContentView(this, layoutRes)
        mViewModel = ViewModelProvider(this, ViewModelFactory(viewModel))[viewModel::class.java]
        viewModelId = onBindVariableId
        //允许设置变量的值而不反映
        binding?.let { binding.setVariable(viewModelId, mViewModel) }
        //让ViewModel拥有View的生命周期感应
        mViewModel?.let { lifecycle.addObserver(it) }
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding?.lifecycleOwner = this
    }

    @get:LayoutRes
    abstract val layoutRes: Int
    abstract val viewModel: VM
    abstract val onBindVariableId: Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()

    //今日头条适配方案
    override fun isBaseOnWidth(): Boolean = true
    override fun getSizeInDp(): Float = ZjConfig.screenWidth

    //可以重写状态栏和导航栏颜色
    // 注：颜色不能使用Color.WHITE设置（报错），必须使用R.color.white
    open val statusBarColor: Int = defaultStatusBarColor
    open val navigationBarColor: Int = defaultNavigationBarColor
    open val fitsSystemWindows: Boolean = true
    open val keyboardListener: OnKeyboardListener? = null

    /**
     * 沉侵式
     */
    private fun initImmersionBar() {
        ImmersionBar.with(this).apply {
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

    /**
     * 沉侵式颜色
     */
    private val setStatusBarColor: List<Int> =
        listOf(R.color.white)
    private val setNavigationBarColor: List<Int> =
        listOf(R.color.white)

    override fun onDestroy() {
        super.onDestroy()
        synchronized(BaseActivity::class.java) { mApplication?.getActivityList()?.remove(this) }
        binding?.let { it.unbind() }
        mViewModel?.let { lifecycle.removeObserver(it) }
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivity(url: String) {
        ARouter.getInstance().build(url).navigation()
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称 (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, type: Int) {
        ARouter.getInstance().build(url).navigation(this, type)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivity(url: String, bundle: Bundle) {
        ARouter.getInstance().build(url).with(bundle).navigation()
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, bundle: Bundle, type: Int) {
        ARouter.getInstance().build(url).with(bundle).navigation(this, type)
    }

    /**
     * 页面跳转 - 清楚该类之前的所有activity
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivityNewTask(url: String) {
        ARouter.getInstance().build(url)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    /**
     * 携带数据的页面跳转 - 清楚该类之前的所有activity
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     */
    fun startActivityNewTask(url: String, bundle: Bundle) {
        ARouter.getInstance().build(url).with(bundle)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    /**
     * 语言适配
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { LanguageUtil().attachBaseContext(it) })
    }

    /**
     * 点击edittext以外区域隐藏软键盘
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(v!!.windowToken, 0)
            }
            return super.dispatchTouchEvent(ev)
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        try {
            if (window.superDispatchTouchEvent(ev)) {
                return true
            }
        } catch (e: Exception) {
        }
        return onTouchEvent(ev)
    }

    private fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val leftTop = intArrayOf(0, 0)
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }
}