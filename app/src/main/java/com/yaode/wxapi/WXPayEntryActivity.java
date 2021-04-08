package com.yaode.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.SPUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zh.kotlin_mvvm.R;
import com.zh.kotlin_mvvm.ui.TestWxPayActivity;
import com.zh.kotlin_mvvm.utils.WxPaySuccessEvent;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI mWxapi;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wx_login);
        mContext = this;
        //弄成透明的
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        mWxapi = WXAPIFactory.createWXAPI(this, SPUtils.getInstance().getString("appId"), false);
        mWxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d(TAG, "onPayFinish,errCode=" + resp.errCode);
            EventBus.getDefault().post(new WxPaySuccessEvent(resp.errCode));
        }
        finish();
    }
}
