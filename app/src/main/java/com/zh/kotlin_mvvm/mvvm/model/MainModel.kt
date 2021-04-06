package com.zh.kotlin_mvvm.mvvm.model

import com.zh.common.base.BaseMapToBody
import com.zh.common.base.BaseObserver
import com.zh.common.base.bean.BaseResponse
import com.zh.common.base.model.BaseModel
import com.zh.kotlin_mvvm.net.ApiManager
import com.zh.kotlin_mvvm.net.INetService
import com.zh.kotlin_mvvm.net.bean.LoginBean
import com.zh.kotlin_mvvm.utils.AliOrderInfo

class MainModel : BaseModel<INetService>(INetService::class.java) {

    fun onLogin(map: Map<String, Any>, observer: BaseObserver<LoginBean>) {
        doNetRequest(getINetServiceAsync().login(BaseMapToBody.convertMapToBody(map)), observer)
    }

    fun onWxPay(orderId: String, observer: BaseObserver<AliOrderInfo>){
        doNetRequest(getINetService().wxPay(ApiManager.wx_pay, orderId, "1"), observer)
    }
}