package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zh.common.base.BaseActivity
import com.zh.common.utils.ToastUtils.showMessage
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.viewmodel.TestWxPayViewModel
import com.zh.kotlin_mvvm.utils.WxPaySuccessEvent
import kotlinx.android.synthetic.main.activity_test_wx_pay.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TestWxPayActivity : BaseActivity<ViewDataBinding, TestWxPayViewModel>() {

    override val layoutRes: Int = R.layout.activity_test_wx_pay
    override val viewModel: TestWxPayViewModel = TestWxPayViewModel()
    override val onBindVariableId: Int = 0

    private var msgApi: IWXAPI? = null

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        msgApi = WXAPIFactory.createWXAPI(this, null)
        msgApi?.registerApp("wx2e1a8c833e05d4d4")
    }

    override fun initData() {
        btnPay.setOnClickListener {
            viewModel.wxPay(
                tvOrderPrepayId.text.toString(),
                tvOrderNonceStr.text.toString(),
                tvOrderTimeStamp.text.toString(),
                tvOrderSign.text.toString(),
                msgApi
            )
        }
    }

    /**
     * 微信支付成功回调
     *
     * @param event
     */
    @Subscribe
    fun onWxPaySuccessEvent(event: WxPaySuccessEvent) {
        when (event.errCode) {
            0 -> showMessage("支付成功")
            -1 -> showMessage("取消失败:" + event.errCode)
            -2 -> showMessage("取消支付")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}