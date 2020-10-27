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
@Suppress("UNCHECKED_CAST")
abstract class BaseModel<T>(service: Class<*>) : IBaseModel {

    private var iNetService: T
//    private var iNetServiceAsync: T
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mClient = BaseApplication.getApplication().mClientModule

    init {
        iNetService = mClient.provideRequestService(mClient)?.create(service) as T
//        iNetServiceAsync = mClient.provideRequestServiceAsync(mClient)?.create(service) as T
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

    /**
     * 同步调用
     */
    fun getINetService(): T {
        return iNetService
    }

    /**
     * 异步调用
     */
//    fun getINetServiceAsync(): T {
//        return iNetServiceAsync
//    }

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