package com.zh.kotlin_mvvm.mvvm.viewmodel

import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.kotlin_mvvm.mvvm.model.MainModel

class TestWxPayViewModel : BaseViewModel<MainModel>(MainModel()) {

    fun wxPay(
        prepayId: String,
        nonceStr: String,
        timeStamp: String,
        sign: String,
        msgApi: IWXAPI?
    ) {
        val request = PayReq()
        request.appId = "wx2e1a8c833e05d4d4"
        request.partnerId = "1603817423"
        request.prepayId = prepayId
        request.packageValue = "Sign=WXPay"
        request.nonceStr = nonceStr
        request.timeStamp = timeStamp
        request.sign = sign
        msgApi?.sendReq(request)
    }
}