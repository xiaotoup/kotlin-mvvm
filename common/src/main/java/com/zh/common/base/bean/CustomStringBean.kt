package com.zh.common.base.bean

/**
 * @author: zxh
 * @date: 2019/6/16
 * @description:
 */
class CustomStringBean : BaseResponse<CustomStringBean>() {
    /**
     * data : {"bussData":false}
     */
    var data: DataBean? = null

    class DataBean {
        /**
         * bussData : false
         */
        var bussData: String? = null
        var resultCode = 0
        var errMsg: String? = null

    }
}