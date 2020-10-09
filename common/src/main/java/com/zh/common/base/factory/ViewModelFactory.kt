package com.zh.common.base.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zh.common.base.viewmodel.BaseViewModel

/**
 * @auth xiaohua
 * @time 2020/10/8 - 14:21
 * @desc 实例化model工厂
 */
class ViewModelFactory<VM : BaseViewModel<*>>(private var viewModel: VM) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(viewModel::class.java)) {
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
