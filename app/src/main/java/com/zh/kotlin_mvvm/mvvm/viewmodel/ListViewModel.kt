package com.zh.kotlin_mvvm.mvvm.viewmodel

import com.zh.common.base.BaseObserver
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.exception.ApiException
import com.zh.common.view.XRecyclerView
import com.zh.kotlin_mvvm.bean.ListBean
import com.zh.kotlin_mvvm.mvvm.model.MainModel
import com.zh.kotlin_mvvm.net.bean.LoginBean

class ListViewModel : BaseViewModel<MainModel>(MainModel()) {

    fun onDoNet(recyclerView: XRecyclerView, list: MutableList<ListBean>) {
        mModel.onLogin(mapOf(), object : BaseObserver<LoginBean>(recyclerView) {
            override fun onISuccess(message: String, response: LoginBean) {
            }

            override fun onIError(e: ApiException) {
//                list.clear()
                val lis = mutableListOf<ListBean>()
                for (i in 0..10) {
                    lis.add(ListBean(i + 10, "${i + 10} data"))
                }
                list.addAll(lis)
                recyclerView.setNewInstance(list)
            }
        })
    }
}