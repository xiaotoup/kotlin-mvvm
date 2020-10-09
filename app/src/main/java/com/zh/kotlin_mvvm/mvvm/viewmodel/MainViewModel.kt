package com.zh.kotlin_mvvm.mvvm.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zh.common.base.BaseObserver
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.exception.ApiException
import com.zh.common.utils.ToastUtils
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.net.bean.LoginBean
import kotlinx.coroutines.launch

class MainViewModel(private val model: MainModel) : BaseViewModel<MainModel>(model) {

    fun doLogin(context: Context, map: Map<String, Any>) {
        model.onLogin(map, object : BaseObserver<LoginBean>(context, true) {
            override fun onISuccess(response: LoginBean) {
                ToastUtils.showMessage(response.data?.bussData.toString())
            }

            override fun onIError(e: ApiException) {

            }
        })
    }
}