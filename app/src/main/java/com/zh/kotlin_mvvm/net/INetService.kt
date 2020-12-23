package com.zh.kotlin_mvvm.net

import com.zh.kotlin_mvvm.net.bean.LoginBean
import com.zh.kotlin_mvvm.utils.AliOrderInfo
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @author: qq363
 * @date: 2019/5/10
 * @description:
 */
interface INetService {
    /**
     *
     * @param body  可以固定死
     * @return
     */
    @POST(ApiManager.APPLOGIN_URL)
    fun login(@Body body: RequestBody): Observable<LoginBean>

    /**
     *
     * @param body  可以固定死
     * @return
     */
    @GET
    fun wxPay(
        @Url url: String,
        @Query("orderId") orderId: String,
        @Query("channelId") channelId: String
    ): Observable<AliOrderInfo>
}