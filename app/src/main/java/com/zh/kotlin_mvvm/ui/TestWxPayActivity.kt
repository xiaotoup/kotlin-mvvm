package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zh.common.base.BaseActivity
import com.zh.common.utils.LiveDataBus
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.viewmodel.TestWxPayViewModel
import kotlinx.android.synthetic.main.activity_test_wx_pay.*

class TestWxPayActivity : BaseActivity<ViewDataBinding>() {

    override val layoutRes: Int = R.layout.activity_test_wx_pay
    override val viewModel = TestWxPayViewModel()

    private var msgApi: IWXAPI? = null

    override fun initView(savedInstanceState: Bundle?) {
        msgApi = WXAPIFactory.createWXAPI(this, null)
        msgApi?.registerApp(tvAppId.text.toString())

        btnPay.setOnClickListener {
            viewModel.wxPay(
                tvAppId.text.toString(),
                tvPartnerId.text.toString(),
                tvOrderPrepayId.text.toString(),
                tvOrderNonceStr.text.toString(),
                tvOrderTimeStamp.text.toString(),
                tvOrderSign.text.toString(),
                msgApi
            )
        }

        SPUtils.getInstance().put("appId", tvAppId.text.toString())
        tvAppId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                SPUtils.getInstance().put("appId", tvAppId.text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        initData()
    }

    private fun initData() {
        /**
         * 微信支付成功回调
         */
        LiveDataBus.get().with("wx_errCode", Int::class.java)
            .observe(this, Observer {
                when (it) {
                    0 -> ToastUtils.showShort("支付成功")
                    -1 -> ToastUtils.showShort("取消失败:$it")
                    -2 -> ToastUtils.showShort("取消支付")
                }
            })
    }
}