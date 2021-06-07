package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ScreenUtils
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.BR
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.databinding.ActivityMainBinding
import com.zh.kotlin_mvvm.mvvm.viewmodel.MainViewModel

@Route(path = ZjConfig.MainActivity)
class MainActivity(
    override val layoutRes: Int = R.layout.activity_main,
    override val viewModel:MainViewModel = MainViewModel(),
    override val viewModelId: Int = BR.viewModel
) : BaseActivity<ActivityMainBinding>() {

    var sid = ObservableField<String>("iiiii")

    override fun initView(savedInstanceState: Bundle?) {
        binding.activity = this
        initData()
    }

    fun initData() {
        ScreenUtils.getAppScreenWidth()
    }

    fun netLogin(view: View) {
        val map = mapOf<String, Any>(
            "mobile" to "13648394964",
            "pwd" to "123456",
            "loginType" to "PASSWORD"
        )
        viewModel.doLogin(map);
    }

    fun netLogin2(view: View) {
        val map = mapOf<String, Any>(
            "mobile" to "13648394964",
            "pwd" to "123456",
            "loginType" to "PASSWORD"
        )
        viewModel.doLogin2(map);
    }

    fun back(view: View) {
        finish()
    }
}
