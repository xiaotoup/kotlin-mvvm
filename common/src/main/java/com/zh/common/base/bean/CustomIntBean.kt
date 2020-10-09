package com.zh.common.base.bean

/**
 * @author: zxh
 * @date: 2019/6/16
 * @description:
 */
class CustomIntBean : BaseResponse<CustomIntBean?>() {
    /**
     * data : {"bussData":false}
     */
    var data: DataBean? = null

    class DataBean {
        /**
         * bussData : false
         */
        var bussData = 0
        var resultCode = 0
        var errMsg: String? = null

    }
}