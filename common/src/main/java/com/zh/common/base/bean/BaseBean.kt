package com.zh.common.base.bean

class BaseBean : BaseResponse<BaseBean>() {
    var data: DataBean? = null

    class DataBean {
        var resultCode = 0
        var errMsg: String? = null
    }
}