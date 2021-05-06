package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zh.common.base.BaseActivity
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.mvvm.viewmodel.TestWxPayViewModel
import com.zh.kotlin_mvvm.utils.WxPaySuccessEvent
import kotlinx.android.synthetic.main.activity_test_wx_pay.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TestWxPayActivity : BaseActivity<ViewDataBinding, TestWxPayViewModel>() {

    override val layoutRes: Int = R.layout.activity_test_wx_pay
    override val viewModel: TestWxPayViewModel = TestWxPayViewModel()

    private var msgApi: IWXAPI? = null

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        msgApi = WXAPIFactory.createWXAPI(this, null)
        msgApi?.registerApp(tvAppId.text.toString())
    }

    override fun initData() {
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
    }

    /**
     * 微信支付成功回调
     *
     * @param event
     */
    @Subscribe
    fun onWxPaySuccessEvent(event: WxPaySuccessEvent) {
        when (event.errCode) {
            0 -> ToastUtils.showShort("支付成功")
            -1 -> ToastUtils.showShort("取消失败:" + event.errCode)
            -2 -> ToastUtils.showShort("取消支付")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}