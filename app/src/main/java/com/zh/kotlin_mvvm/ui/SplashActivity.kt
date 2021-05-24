package com.zh.kotlin_mvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.dialog.TestDialog
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<ViewDataBinding, NormalViewModel>() {

    override val layoutRes = R.layout.activity_splash

    override val viewModel: NormalViewModel = NormalViewModel()

    override fun initView(savedInstanceState: Bundle?) {
        ToastUtils.showShort("启动了")
        val message = "{\"code\":200, \"message\":\"提示内容\",\"data\":{\"content\":\"哈哈哈哈\"}}"
        LogUtils.wTag("okhttp", message)
        initData()
    }

    @SuppressLint("WrongConstant")
    private fun initData() {
        btnToLogin.setOnClickListener {
            startActivity(ZjConfig.MainActivity)
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
            requestPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionGranted() {
        super.onPermissionGranted()
        startActivity(TestALiPayActivity::class.java)
    }

    fun clickTest(view: View) {
        startActivity(TestActivity::class.java)
    }
}