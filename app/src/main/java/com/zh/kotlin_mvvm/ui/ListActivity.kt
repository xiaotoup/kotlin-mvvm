package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.zh.common.base.BaseActivity
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.mvvm.viewmodel.MainViewModel

@Route(path = ZjConfig.ListActivity)
class ListActivity : BaseActivity<ViewDataBinding, MainViewModel>() {

    override val layoutRes: Int
        get() = R.layout.activity_list

    override fun viewModel(): MainViewModel {
        return MainViewModel(MainModel())
    }

    override val onBindVariableId: Int
        get() = 0

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
    }
}