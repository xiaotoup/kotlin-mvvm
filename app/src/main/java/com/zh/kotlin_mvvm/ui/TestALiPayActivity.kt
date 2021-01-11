package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import com.google.gson.Gson
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.utils.ToastUtils
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.bean.AliPlayResult
import com.zh.kotlin_mvvm.bean.PayResult
import kotlinx.android.synthetic.main.activity_test_a_li_pay.*


class TestALiPayActivity(
    override val layoutRes: Int = R.layout.activity_test_a_li_pay,
    override val viewModel: NormalViewModel = NormalViewModel(),
    override val onBindVariableId: Int = 0
) : BaseActivity<ViewDataBinding, NormalViewModel>() {

    private val SDK_PAY_FLAG = 117

    override fun initView(savedInstanceState: Bundle?) {
        //支付宝沙箱，正式环境得去掉
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
    }

    override fun initData() {
        btnPay.setOnClickListener {
            goAliPay()
        }
    }

    //发起支付宝支付
    private fun goAliPay() {
        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private val payRunnable = Runnable {
        val orderid = tvOrderSign.text.toString()
        val alipay = PayTask(this)
        val result: Map<String, String> = alipay.payV2(orderid, true)
        val msg = Message()
        msg.what = SDK_PAY_FLAG
        msg.obj = result
        mHandler?.sendMessage(msg)
    }

    private val mHandler: Handler = object : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == SDK_PAY_FLAG) {
                val rawResult = msg.obj as Map<String, String>
                val payResult = PayResult(rawResult)
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                Log.d("payResult", payResult.toString())
                val resultInfo: String = payResult.result // 同步返回需要验证的信息
                var out_trade_no: String? = null
                try {
                    val gson = Gson()
                    val aliPlayResult: AliPlayResult =
                        gson.fromJson(resultInfo, AliPlayResult::class.java)
                    out_trade_no =
                        aliPlayResult.alipay_trade_app_pay_response.out_trade_no
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val resultStatus: String = payResult.resultStatus
                if (TextUtils.equals(resultStatus, "9000")) {
                    ToastUtils.showMessage("支付成功")
                } else {
                    ToastUtils.showMessageLong("支付失败: $payResult")
                }
            }
        }
    }
}