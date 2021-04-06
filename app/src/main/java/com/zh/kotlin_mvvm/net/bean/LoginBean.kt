package com.zh.kotlin_mvvm.net.bean

data class LoginBean(
    var bussData: String? = null,
    var resultCode: Int = 0
) {
    /**
     * data : {"bussData":"YrDPQB1ok03SvYsba59+aU8E2QV791AVnxVw3eZnZ6XaPo3ecsM/pl8EaL5zjaXWb42EAwONlSU=","resultCode":200}
     */
    override fun toString(): String {
        return "DataBean{" +
                "bussData='" + bussData + '\'' +
                ", resultCode=" + resultCode +
                '}'
    }
}