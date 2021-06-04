package com.zh.kotlin_mvvm.mvvm.viewmodel

import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.zh.common.base.viewmodel.BaseViewModel

class TestWxPayViewModel : BaseViewModel() {

    fun wxPay(
        appId: String,
        partnerId: String,
        prepayId: String,
        nonceStr: String,
        timeStamp: String,
        sign: String,
        msgApi: IWXAPI?
    ) {
        val request = PayReq()
        request.appId = appId
        request.partnerId = partnerId
        request.prepayId = prepayId
        request.packageValue = "Sign=WXPay"
        request.nonceStr = nonceStr
        request.timeStamp = timeStamp
        request.sign = sign
        msgApi?.sendReq(request)
    }
}