package com.zh.kotlin_mvvm.utils;

public class WxPaySuccessEvent {
    private int errCode;

    public WxPaySuccessEvent(int errCode) {
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

}
