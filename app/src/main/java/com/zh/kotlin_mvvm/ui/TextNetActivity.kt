package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.facade.annotation.Route
import com.zh.common.base.BaseActivity
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.BR
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.databinding.ActivityTextMainBinding
import com.zh.kotlin_mvvm.mvvm.viewmodel.MainViewModel

@Route(path = ZjConfig.TextNetActivity)
class TextNetActivity(
    override val layoutRes: Int = R.layout.activity_text_main,
    override val viewModel: MainViewModel = MainViewModel(),
    override val viewModelId: Int = BR.viewModel
) : BaseActivity<ActivityTextMainBinding>() {

    var sid = ObservableField<String>("iiiii")
    var sid2 = MutableLiveData<String>("点我试试?")

    override fun initView(savedInstanceState: Bundle?) {
        binding.activity = this
    }

    fun netLogin(view: View) {
        sid2.value = "开始请求"
        val map = mapOf<String, Any>(
            "mobile" to "13648394964",
            "pwd" to "123456",
            "loginType" to "PASSWORD"
        )
        viewModel.doLogin(map)
    }

    fun netLogin2(view: View) {
        val map = mapOf<String, Any>(
            "mobile" to "13648394964",
            "pwd" to "123456",
            "loginType" to "PASSWORD"
        )
        viewModel.doLogin2(map)
    }

    fun back(view: View) {
        finish()
    }
}