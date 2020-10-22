package com.zh.common.base

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.zh.common.base.factory.ViewModelFactory
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.utils.ScreenUtils
import com.zh.config.ZjConfig
import me.jessyan.autosize.internal.CustomAdapt


abstract class BaseDialogFragment<BINDING : ViewDataBinding, VM : BaseViewModel<*>> :
    DialogFragment(), CustomAdapt {

    private lateinit var binding: BINDING
    var mViewModel: VM? = null
    private var viewModelId = 0
    private lateinit var mContext: Context
    private var rootView: View? = null

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
        if (isAdded) {
            initView(savedInstanceState)
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
            setCancelable(false)
        }
        return dialog
    }

    @get:LayoutRes
    abstract val layoutRes: Int
    abstract val viewModel: VM
    abstract val onBindVariableId: Int
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()

    private fun initViewDataBinding(inflater: LayoutInflater, container: ViewGroup?) {
        if (layoutRes != 0) binding =
            DataBindingUtil.inflate<BINDING>(inflater, layoutRes, container, false)
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
    open fun getRootView(): View? = rootView

    override fun onStart() {
        super.onStart()
        // 全屏显示Dialog，重新测绘宽高
//        if (dialog != null) {
//            val dm = DisplayMetrics()
//            activity!!.windowManager.defaultDisplay.getMetrics(dm)
//            dialog!!.window?.setLayout(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            ScreenUtils
//        }
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
    }
}