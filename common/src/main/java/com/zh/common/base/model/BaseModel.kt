package com.zh.common.base.model

import com.zh.common.base.BaseApplication
import com.zh.common.base.BaseObserver
import com.zh.common.base.bean.BaseResponse
import com.zh.common.exception.ResponseTransformer
import com.zh.common.schedulers.SchedulerProvider
import com.zh.common.utils.LogUtil
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:03
 * @desc model基类
 */
abstract class BaseModel<T> : IBaseModel {

    private var iNetService: T
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mClientModule = BaseApplication.getApplication().mClientModule

    constructor(service: Class<*>) {
        iNetService = mClientModule.provideRequestService(mClientModule)?.create(service) as T
    }

    //添加网络请求到CompositeDisposable
    fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable?.also {
            LogUtil.d("--okhttp--", "disposable is add")
            it.add(disposable)
        }
    }

    override fun onCleared() {
        //解除网络请求
        mCompositeDisposable?.also {
            LogUtil.d("--okhttp--", "disposable is clear")
            mCompositeDisposable.clear()
        }
    }

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