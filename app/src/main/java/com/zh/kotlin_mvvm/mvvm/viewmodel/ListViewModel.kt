package com.zh.kotlin_mvvm.mvvm.viewmodel

import com.zh.common.base.BaseObserver
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.exception.ApiException
import com.zh.common.view.XRecyclerView
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.net.bean.LoginBean

class ListViewModel : BaseViewModel<MainModel>(MainModel()) {

    fun onDoNet(recyclerView: XRecyclerView) {
        mModel.onLogin(mapOf(), object : BaseObserver<LoginBean>(recyclerView) {
            override fun onISuccess(message: String, response: LoginBean) {
            }

            override fun onIError(e: ApiException) {
            }
        })
    }
}