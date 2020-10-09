package com.zh.kotlin_mvvm.net.bean

import com.zh.common.base.bean.BaseResponse

class LoginBean : BaseResponse<LoginBean>() {
    /**
     * data : {"bussData":"YrDPQB1ok03SvYsba59+aU8E2QV791AVnxVw3eZnZ6XaPo3ecsM/pl8EaL5zjaXWb42EAwONlSU=","resultCode":200}
     */
    var data: DataBean? = null

    class DataBean {
        /**
         * bussData : YrDPQB1ok03SvYsba59+aU8E2QV791AVnxVw3eZnZ6XaPo3ecsM/pl8EaL5zjaXWb42EAwONlSU=
         * resultCode : 200
         */
        var bussData: String? = null
        var resultCode = 0

        override fun toString(): String {
            return "DataBean{" +
                    "bussData='" + bussData + '\'' +
                    ", resultCode=" + resultCode +
                    '}'
        }
    }

    override fun toString(): String {
        return "LoginBean{" +
                "data=" + data +
                '}'
    }
}