package com.zh.kotlin_mvvm.mvvm.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.zh.common.base.BaseObserver
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.exception.ApiException
import com.zh.common.utils.ToastUtils
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.net.bean.LoginBean

class MainViewModel : BaseViewModel<MainModel>(MainModel()) {

    var sid: ObservableField<String> = ObservableField("")
    var imgUrl: ObservableInt = ObservableInt()

    init {
        imgUrl.set(R.mipmap.teenmodel_bg_4)
        sid.set("初始化")
    }

    fun back(view: View) {
        /*CustomDialog.Builder(mContext!!)
             .setMessage("是否退出?")
             .setPositiveButton("确认", DialogInterface.OnClickListener { p0, p1 ->
                 (mContext as Activity).finish()
                 p0?.dismiss()
             })
             .setNegativeButton("取消",
                 DialogInterface.OnClickListener { p0, p1 -> p0?.dismiss() }).create().show()*/
    }

    fun doLogin(context: Context, map: Map<String, Any>) {
        mModel.onLogin(map, object : BaseObserver<LoginBean>(context, true) {

            override fun onISuccess(message: String, response: LoginBean) {
                sid.set(response.bussData)
                ToastUtils.showMessage("code=${message}")
            }

            override fun onIError(e: ApiException) {
                sid.set(e.message)
                ToastUtils.showMessage("code=${e.message}")
            }
        })
    }
}