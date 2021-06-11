package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.*
import com.luck.picture.lib.tools.DoubleUtils
import com.zh.common.base.BaseActivity
import com.zh.common.base.BasePopWindow
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.dialog.TestDialog
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<ViewDataBinding>() {

    override val layoutRes = R.layout.activity_splash

    override val viewModel: BaseViewModel
        get() = NormalViewModel()

    override fun initView(savedInstanceState: Bundle?) {
        ToastUtils.showShort("启动了")
        val message = "{\"code\":200, \"message\":\"提示内容\",\"data\":{\"content\":\"哈哈哈哈\"}}"
        LogUtils.wTag("okhttp", message)
        initData()
    }

    private fun initData() {
        btnToLogin.setOnClickListener {
            startActivity(ZjConfig.MainActivity)
        }
        btnNet.setOnClickListener {
            startActivity(ZjConfig.TextNetActivity)
        }
        btnLogin.setOnClickListener {
            startActivity(ZjConfig.LoginActivity)
        }
        btnList.setOnClickListener {
            startActivity(ZjConfig.ListActivity)
        }
        btnDialog.setOnClickListener {
            TestDialog().show(supportFragmentManager, "s")
        }
        btnWxPay.setOnClickListener {
            startActivity(TestWxPayActivity::class.java)
        }
        btnALiPay.setOnClickListener {
            startActivity(TestALiPayActivity::class.java)
        }
        btnPicture.setOnClickListener {
            startActivity(PictureActivity::class.java)
        }
        btnPopupWindow.setOnClickListener {
            openPopupWindow()
        }
    }

    fun clickTest(view: View) {
        startActivity(TestActivity::class.java)
    }

    private fun openPopupWindow() {
        if (DoubleUtils.isFastDoubleClick()) return
        BasePopWindow(this)
            .createView(
                R.layout.layout_pop,
                SizeUtils.dp2px(150f),
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            .showAsDropDown(btnPopupWindow, 0, 0, Gravity.TOP)
    }
}