package com.zh.common.base.model

import com.zh.common.http.INetService

/**
 * 空的Model，用于不需要操作的类使用
 */
class NormalModel :BaseModel<INetService>(INetService::class.java){

}