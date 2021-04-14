package com.zh.common.view.listener

interface INetCallbackView {
    fun onLoadingView(show: Boolean)
    fun onFailure(errMsg: String?)
    fun onNoNetWork()
}