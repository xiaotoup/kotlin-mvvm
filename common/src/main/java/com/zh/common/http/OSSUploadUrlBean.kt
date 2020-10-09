package com.zh.common.http

import com.zh.common.base.bean.BaseResponse
import java.io.Serializable

class OSSUploadUrlBean : BaseResponse<OSSUploadUrlBean>() {
    var data: DataBean? = null

    override fun toString(): String {
        return "LoginBean{" +
                "data=" + data +
                '}'
    }

    class DataBean {
        /**
         * bussData : {"uploadUrl":"http://icebar-chugou.oss-cn-shenzhen.aliyuncs.com/icebar-chugo/3.jpg?Expires=1530003125&OSSAccessKeyId=LTAIBAHYUefLVanL&Signature=zvdpFiGqS0%2BFb8ygmeSncVXjSjI%3D","downloadUrl":"https://chugou.icebartech.com/file/icebar-chugo/3.jpg","fileKey":"icebar-chugo/3.jpg"}
         */
        var bussData: BussDataBean? = null

        class BussDataBean : Serializable {
            /**
             * uploadUrl : http://icebar-chugou.oss-cn-shenzhen.aliyuncs.com/icebar-chugo/3.jpg?Expires=1530003125&OSSAccessKeyId=LTAIBAHYUefLVanL&Signature=zvdpFiGqS0%2BFb8ygmeSncVXjSjI%3D
             * downloadUrl : https://chugou.icebartech.com/file/icebar-chugo/3.jpg
             * fileKey : icebar-chugo/3.jpg
             */
            var uploadUrl: String? = null
            var downloadUrl: String? = null
            var fileKey: String? = null

        }
    }
}