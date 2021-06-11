package com.zh.common.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.zh.common.base.BaseObserver
import com.zh.common.base.bean.BaseResponse
import com.zh.common.di.RetrofitManager
import com.zh.common.exception.ResponseTransformer
import com.zh.common.schedulers.SchedulerProvider
import com.zh.config.ZjConfig
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:02
 * @desc ViewModel基类
 */
open class BaseViewModel : ViewModel() {

    var pageIndex = 1
    var pageSize = 10
    private var isAddDisposable = false
    private val mCompositeDisposable = CompositeDisposable()

    //添加网络请求到CompositeDisposable
    private fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.also {
            Log.e("--okhttp--", "disposable is add")
            isAddDisposable = true
            it.add(disposable)
        }
    }

    override fun onCleared() {
        //解除网络请求
        mCompositeDisposable.also {
            if (isAddDisposable) {
                Log.e("--okhttp--", "disposable is clear")
                isAddDisposable = false
                mCompositeDisposable.clear()
            }
        }
    }

    /**
     * 实例化网络请求
     * hostUrl 域名, 默认ZjConfig.base_url，需要修改传入新的域名（新的每次都传）
     */
    inline fun <reified T : Any> apiService(hostUrl: String = ZjConfig.base_url): T =
        RetrofitManager.instance.apiService(T::class.java, hostUrl)

    /**
     * 公用的网络请求发起的操作
     * @param observable 发起请求的被观察着
     * @param observer 观察着回调
     */
    fun <R> doNetRequest(observable: Observable<out BaseResponse<R>>, observer: BaseObserver<R>) {
        val subscribeWith = observable
            .compose(ResponseTransformer.instance.handleResult())
            .compose(SchedulerProvider.instance.applySchedulers())
            .subscribeWith(observer)
        subscribeWith.getDisposable()?.let { addSubscribe(it) }
    }
}

