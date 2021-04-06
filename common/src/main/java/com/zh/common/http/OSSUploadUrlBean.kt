package com.zh.common.http

import java.io.Serializable

data class OSSUploadUrlBean(var bussData: BussDataBean? = null) {
    class BussDataBean(nothing: Nothing?) : Serializable {
        /**
         * uploadUrl : http://icebar-chugou.oss-cn-shenzhen.aliyuncs.com/icebar-chugo/3.jpg?Expires=1530003125&OSSAccessKeyId=LTAIBAHYUefLVanL&Signature=zvdpFiGqS0%2BFb8ygmeSncVXjSjI%3D
         * downloadUrl : https://chugou.icebartech.com/file/icebar-chugo/3.jpg
         * fileKey : icebar-chugo/3.jpg
         */
        var uploadUrl: String? = nothing
        var downloadUrl: String? = nothing
        var fileKey: String? = nothing
    }
}