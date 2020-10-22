package com.zh.kotlin_mvvm.net

import com.zh.common.http.INetService
import com.zh.kotlin_mvvm.net.bean.LoginBean
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author: qq363
 * @date: 2019/5/10
 * @description:
 */
interface INetService : INetService{
    /**
     *
     * @param body  可以固定死
     * @return
     */
    @POST(ApiManager.APPLOGIN_URL)
    fun login(@Body body: RequestBody): Observable<LoginBean>
}