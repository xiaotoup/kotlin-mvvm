package com.zh.common.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter
import com.zh.common.R
import com.zh.common.base.factory.ViewModelFactory
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.utils.ScreenUtils
import com.zh.config.ZjConfig
import me.jessyan.autosize.internal.CustomAdapt


abstract class BaseDialogFragment<VM : BaseViewModel<*>> :
    DialogFragment(), CustomAdapt {

    private val TAG = "BaseDialogFragment"
    private lateinit var binding: ViewDataBinding
    var mViewModel: VM? = null
    private var viewModelId = 0
    private var rootView: View? = null
    lateinit var mContext: Context

    @get:LayoutRes
    abstract val layoutRes: Int
    abstract val marginWidth: Int//diaog到两边的距离,设置一边的距离
    abstract val viewModel: VM
    abstract val onBindVariableId: Int
    abstract fun initView(savedInstanceState: Bundle?, view: View)
    abstract fun initData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.apply {
            try {
                // 解决Dialog内D存泄漏
                setOnDismissListener(null)
                setOnCancelListener(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.StyleDialog)
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
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
        isCancelable = true
        if (isAdded) {
            initView(savedInstanceState, view)
            initData()
        }
    }

    /**
     * 全屏显示Dialog
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawableResource(R.color.transparent)
            setCanceledOnTouchOutside(true)
            setCancelable(true)
        }
        return dialog
    }

    private fun initViewDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        if (layoutRes != 0) binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRes, container, false)
        mViewModel = ViewModelProvider(this, ViewModelFactory(viewModel))[viewModel::class.java]
        viewModelId = onBindVariableId
        //允许设置变量的值而不反映
        binding?.let { binding.setVariable(viewModelId, mViewModel) }
        //让ViewModel拥有View的生命周期感应
        mViewModel?.let { lifecycle.addObserver(it) }
        //支持LiveData绑定xml，数据改变，UI自动会更新
        binding.lifecycleOwner = this
    }

    //今日头条适配方案
    override fun isBaseOnWidth(): Boolean = true
    override fun getSizeInDp(): Float = ZjConfig.screenWidth

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ScreenUtils.screenRealWidth - 2 * ScreenUtils.dip2px(marginWidth.toFloat()),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onPause() {
        super.onPause()
        onDestroyView()
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
        binding?.let { it.unbind() }
        mViewModel?.let { lifecycle.removeObserver(it) }
        dialog?.dismiss()
    }

    fun setBottomAnimation() {
        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setWindowAnimations(R.style.StyleBottomAnimation)
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
     * 第二个参数则是RequestCode
     */
    fun startActivityForResult(url: String, bundle: Bundle, type: Int) {
        val intent = Intent(context, getDestination(url))
        intent.putExtras(bundle)
        startActivityForResult(intent, type)
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
     * 由于ARouter不支持Fragment startActivityForResult(),需要获取跳转的Class
     * 根据路径获取具体要跳转的class
     */
    private fun getDestination(url: String): Class<*> {
        val postcard = ARouter.getInstance().build(url)
        LogisticsCenter.completion(postcard)
        return postcard.destination
    }
}