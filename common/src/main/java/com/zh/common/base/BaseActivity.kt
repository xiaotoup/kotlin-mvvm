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
import com.luck.picture.lib.tools.DoubleUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zh.common.base.factory.ViewModelFactory
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.utils.JumpActivity
import com.zh.common.utils.LanguageUtil


/**
 * @auth xiaohua
 * @time 2020/10/7 - 15:21
 * @desc Activity基类，MVVM架构
 */
abstract class BaseActivity<BINDING : ViewDataBinding, VM : BaseViewModel<*>> :
    RxAppCompatActivity(), JumpActivity {

    lateinit var binding: BINDING
    var mViewModel: VM? = null
    private var viewModelId = 0
    private val isNotAddActivityList = "is_add_activity_list" //是否加入到activity的list，管理
    private var mApplication: BaseApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mApplication = application as BaseApplication
        //添加到栈管理
        val isNotAdd = intent.getBooleanExtra(isNotAddActivityList, false)
        synchronized(BaseActivity::class.java) {
            if (!isNotAdd) mApplication?.getActivityList()?.add(this)
        }
        initViewDataBinding()
        initImmersionBars()
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
    open val onBindVariableId: Int = 0
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        synchronized(BaseActivity::class.java) { mApplication?.getActivityList()?.remove(this) }
        binding?.let { it.unbind() }
        mViewModel?.let { lifecycle.removeObserver(it) }
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称 (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).navigation(this, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(this, classActivity), type)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        ARouter.getInstance().build(url).with(bundle).navigation(this, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(this, classActivity).putExtras(bundle), type)
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