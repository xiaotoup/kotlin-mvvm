package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.zh.common.base.BaseActivity
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.BR
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.viewmodel.MainViewModel

@Route(path = ZjConfig.MainActivity)
class MainActivity : BaseActivity<ViewDataBinding, MainViewModel>() {

    override val layoutRes: Int = R.layout.activity_main
    override val onBindVariableId: Int = BR.viewModel
    override val viewModel: MainViewModel = MainViewModel()
    override fun initView(savedInstanceState: Bundle?) {
        mViewModel?.setContext(this)
    }

    override fun initData() {

    }

    fun netLogin(view: View) {
        val map = mapOf<String, Any>(
            "mobile" to "13648394964",
            "pwd" to "123456",
            "loginType" to "PASSWORD"
        )
        mViewModel?.doLogin(this, map);
    }
}