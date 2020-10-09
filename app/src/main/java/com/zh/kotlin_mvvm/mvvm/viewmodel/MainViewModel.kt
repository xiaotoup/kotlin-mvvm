package com.zh.kotlin_mvvm.mvvm.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import com.zh.common.base.BaseObserver
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.exception.ApiException
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.net.bean.LoginBean

class MainViewModel(private val model: MainModel) : BaseViewModel<MainModel>(model) {

    var sid: ObservableField<String> = ObservableField("")
    var mContext : Context? = null

    fun setContext(context: Context){
        mContext = context
    }

    fun back(view: View) {
        (mContext as Activity).finish()
    }

    fun doLogin(context: Context, map: Map<String, Any>) {
        model.onLogin(map, object : BaseObserver<LoginBean>(context, true) {
            override fun onISuccess(response: LoginBean) {
                sid.set(response.data?.bussData)
            }

            override fun onIError(e: ApiException) {
                sid.set(e.message)
            }
        })
    }
}