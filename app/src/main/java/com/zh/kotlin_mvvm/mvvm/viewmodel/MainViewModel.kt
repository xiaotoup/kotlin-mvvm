package com.zh.kotlin_mvvm.mvvm.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.zh.common.base.BaseObserver
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.exception.ApiException
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.net.bean.LoginBean

class MainViewModel : BaseViewModel<MainModel>(MainModel()) {

    private var mContext: Context? = null
    var sid: ObservableField<String> = ObservableField("")
    var imgUrl: ObservableInt = ObservableInt()

    fun setContext(context: Context) {
        mContext = context
        imgUrl.set(R.mipmap.teenmodel_bg_4)
    }

    fun back(view: View) {
        (mContext as Activity).finish()
    }

    fun doLogin(context: Context, map: Map<String, Any>) {
        mModel?.onLogin(map, object : BaseObserver<LoginBean>(context, true) {
            override fun onISuccess(response: LoginBean) {
                sid.set(response.data?.bussData)
            }

            override fun onIError(e: ApiException) {
                sid.set(e.message)
            }
        })
    }
}