package com.zh.common.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.immersionbar.ImmersionBar
import com.luck.picture.lib.tools.DoubleUtils
import com.zh.common.R
import com.zh.common.base.factory.ViewModelFactory
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.utils.JumpActivity
import com.zh.common.view.dialog.LoadingDialog


abstract class BaseDialogFragment<BINDING : ViewDataBinding> :
    DialogFragment(), JumpActivity {

    private val TAG = "BaseDialogFragment"
    lateinit var binding: BINDING
    private var rootView: View? = null
    lateinit var mContext: Context
    private var loadingDialog: LoadingDialog? = null

    @get:LayoutRes
    abstract val layoutRes: Int
    open val marginHorizontal: Int = 30 //dialog到两边的距离,设置一边的距离即可
    open val viewModel: BaseViewModel = NormalViewModel()
    open val viewModelId = 0
    open val isBottomInto = false // 默认中间弹出
    abstract fun initView(savedInstanceState: Bundle?, view: View)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.apply {
            try {
                setDialogAnimation()
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
        initView(savedInstanceState, view)
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
        binding?.unbind()
        dialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.destroy(this)
    }

    //外部点击消失
    fun isCanceledOnTouchOutside(isOutside: Boolean = true) {
        dialog?.setCanceledOnTouchOutside(isOutside)
    }

    //弹出动画 - 默认中间弹出
    private fun setDialogAnimation() {
        dialog?.window?.let {
            if (isBottomInto) {
                it.setLayout(
                    ScreenUtils.getScreenWidth(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                it.setGravity(Gravity.BOTTOM)
                it.setWindowAnimations(android.R.style.Animation_InputMethod)
            } else {
                it.setLayout(
                    ScreenUtils.getScreenWidth() - SizeUtils.dp2px(
                        2 * marginHorizontal.toFloat()
                    ),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                it.setGravity(Gravity.CENTER)
                it.setWindowAnimations(android.R.style.Animation_Toast)
            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            //防止连续点击add多个fragment
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    private fun getLoadingDialog() {
        loadingDialog ?: also { loadingDialog = LoadingDialog(mContext) }
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