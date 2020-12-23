package com.zh.kotlin_mvvm.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.utils.ToastUtils
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.dialog.TestDialog
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<ViewDataBinding, NormalViewModel>() {

    override val layoutRes = R.layout.activity_splash

    override val viewModel: NormalViewModel = NormalViewModel()

    override val onBindVariableId = 0

    override fun initView(savedInstanceState: Bundle?) {
        ToastUtils.showMessage("启动了")
    }

    override fun initData() {
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
            startActivity(Intent(this, TestWxPayActivity::class.java))
        }
    }
}