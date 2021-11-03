package com.zh.common.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.tools.DoubleUtils
import com.trello.rxlifecycle2.components.support.RxFragment
import com.zh.common.base.factory.ViewModelFactory
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.immersion.ImmersionOwner
import com.zh.common.immersion.ImmersionProxy
import com.zh.common.utils.JumpActivity
import com.zh.common.view.dialog.LoadingDialog

/**
 * ......................我佛慈悲....................
 * ......................_oo0oo_.....................
 * .....................o8888888o....................
 * .....................88" . "88....................
 * .....................(| -_- |)....................
 * .....................0\  =  /0....................
 * ...................___/`---'\___..................
 * ..................' \\|     |// '.................
 * ................./ \\|||  :  |||// \..............
 * .............../ _||||| -卍-|||||- \..............
 * ..............|   | \\\  -  /// |   |.............
 * ..............| \_|  ''\---/''  |_/ |.............
 * ..............\  .-\__  '-'  ___/-. /.............
 * ............___'. .'  /--.--\  `. .'___...........
 * .........."" '<  `.___\_<|>_/___.' >' ""..........
 * ........| | :  `- \`.;`\ _ /`;.`/ - ` : | |.......
 * ........\  \ `_.   \_ __\ /__ _/   .-` /  /.......
 * ....=====`-.____`.___ \_____/___.-`___.-'=====....
 * ......................`=---='.....................
 * ..................佛祖开光 ,永无BUG................
 * @auth xiaohua
 * @time 2020/10/7 - 15:21
 * @desc Fragment基类，MVVM架构
 */
abstract class BaseFragment<BINDING : ViewDataBinding> : RxFragment(),
    JumpActivity, ImmersionOwner {

    lateinit var binding: BINDING
    private var rootView: View? = null
    private lateinit var mContext: Context
    private var loadingDialog: LoadingDialog? = null

    //ImmersionBar代理类
    private val mImmersionProxy = ImmersionProxy(this)

    override fun initImmersionBar() {
        initImmersionBars()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mImmersionProxy.isUserVisibleHint = isVisibleToUser
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mImmersionProxy.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ARouter.getInstance().inject(this)
        if (null == rootView) { //如果缓存中有rootView则直接使用
            initViewDataBinding(inflater, container)
            this.rootView = binding.root;
        } else {
            rootView?.let {
                it.parent?.let { it2 -> (it2 as ViewGroup).removeView(it) }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //在OnCreate方法中调用下面方法，然后再使用线程，就能在uncaughtException方法中捕获到异常
        initView(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mImmersionProxy.onActivityCreated(savedInstanceState)
    }

    private fun initViewDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        if (layoutRes != 0) binding =
            DataBindingUtil.inflate(inflater, layoutRes, container, false)
        val mViewModel = ViewModelProvider(this, ViewModelFactory(viewModel))[viewModel::class.java]
        //允许设置变量的值而不反映
        binding?.setVariable(viewModelId, mViewModel)
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding?.lifecycleOwner = this
    }

    @get:LayoutRes
    abstract val layoutRes: Int
    open val viewModel: BaseViewModel = NormalViewModel()
    open val viewModelId = 0
    abstract fun initView(savedInstanceState: Bundle?)

    open fun getRootView(): View? = rootView

    override fun onResume() {
        super.onResume()
        mImmersionProxy.onResume()
    }

    override fun onPause() {
        super.onPause()
        mImmersionProxy.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //为rootView做缓存，在viewpager中使用fragment时可以提升切换流畅度
        rootView?.let {
            it.parent?.let { it2 -> (it2 as ViewGroup).removeView(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mImmersionProxy.onDestroy()
        binding?.unbind()
        ImmersionBar.destroy(this)
    }

    /**
     * 页面跳转
     *
     * @param url 对应组建的名称 (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        val intent = Intent(context, getDestination(url))
        startActivityForResult(intent, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(activity, classActivity), type)
    }

    /**
     * 携带数据的页面跳转
     *
     * @param url 对应组建的名称  (“/mine/setting”)
     * navigation的第一个参数***必须是Activity***，第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        val intent = Intent(context, getDestination(url))
        intent.putExtras(bundle)
        startActivityForResult(intent, type)
    }

    //不使用路由跳转
    fun startActivityForResult(classActivity: Class<*>, bundle: Bundle, type: Int) {
        if (DoubleUtils.isFastDoubleClick()) return
        startActivityForResult(Intent(activity, classActivity).putExtras(bundle), type)
    }

    /**
     * 由于ARouter不支持Fragment startActivityForResult(),需要获取跳转的Class
     * 根据路径获取具体要跳转的class
     */
    private fun getDestination(url: String): Class<*> {
        val postcard = ARouter.getInstance().build(url)
        LogisticsCenter.completion(postcard)
        return postcard.destination
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mImmersionProxy.onHiddenChanged(hidden)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mImmersionProxy.onConfigurationChanged(newConfig)
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    override fun onLazyBeforeView() {}

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    override fun onLazyAfterView() {}

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    override fun onVisible() {}

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    override fun onInvisible() {}

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    override fun immersionBarEnabled(): Boolean = true

    private fun getLoadingDialog() {
        loadingDialog ?: also { loadingDialog = LoadingDialog(context!!) }
    }

    /**
     * 显示加载dialog
     */
    fun showLoading() {
        try {
            getLoadingDialog()
            loadingDialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 结束dialog
     */
    fun dismissLoading() {
        try {
            loadingDialog?.let { if (it.isShowing) it.dismiss() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}