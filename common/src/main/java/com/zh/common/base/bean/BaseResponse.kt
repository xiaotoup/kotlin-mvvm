package com.zh.common.base.bean

import com.zh.common.exception.StatusCode

/**
 * @author: qq363
 * @date: 2019/5/10
 * @description:
 */
data class BaseResponse<T>(var status: Int = 0, var msg: String = "", var data: T) {
    //请求成功返回数据
    val isSuccess: Boolean = (status == StatusCode.STATUS_CODE_SUCCESS)
}