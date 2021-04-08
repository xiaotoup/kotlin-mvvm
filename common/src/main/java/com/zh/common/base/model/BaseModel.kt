package com.zh.common.base.model

import com.blankj.utilcode.util.LogUtils
import com.zh.common.base.BaseObserver
import com.zh.common.base.bean.BaseResponse
import com.zh.common.di.ClientModule
import com.zh.common.exception.ResponseTransformer
import com.zh.common.schedulers.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:03
 * @desc model基类
 */
abstract class BaseModel<T>(service: Class<*>) : IBaseModel {

    private val iNetService: T = ClientModule.instance.netRequest(service)
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    //添加网络请求到CompositeDisposable
    private fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.also {
            LogUtils.d("--okhttp--", "disposable is add")
            it.add(disposable)
        }
    }

    override fun onCleared() {
        //解除网络请求
        mCompositeDisposable.also {
            LogUtils.d("--okhttp--", "disposable is clear")
            mCompositeDisposable.clear()
        }
    }

    /**
     * 同步调用
     */
    fun getINetService(): T {
        return iNetService
    }

    /**
     * 公用的网络请求发起的操作
     */
    fun <R> doNetRequest(observable: Observable<out BaseResponse<R>>, observer: BaseObserver<R>) {
        val subscribeWith = observable
            .compose(ResponseTransformer.handleResult())
            .compose(SchedulerProvider.instance.applySchedulers())
            .subscribeWith(observer)
        subscribeWith.getDisposable()?.let { addSubscribe(it) }
    }
}