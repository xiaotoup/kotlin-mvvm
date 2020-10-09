package com.zh.common.base.model

import com.zh.common.base.BaseApplication
import com.zh.common.utils.LogUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:03
 * @desc model基类
 */
abstract class BaseModel<T> : IBaseModel {

    private var iNetService: T
    private var mCompositeDisposable: CompositeDisposable? = null

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    constructor(service: Class<*> ){
        iNetService = BaseApplication.getApplication().mClientModule
            .provideRequestService(BaseApplication.getApplication().mClientModule)?.create(service) as T
    }

    //添加网络请求到CompositeDisposable
    fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }


    override fun onCleared() {
        //解除网络请求
        if (mCompositeDisposable != null) {
            mCompositeDisposable?.clear()
            LogUtil.d("--okHttp--", "disposable is clear")
        }
    }

    fun getINetService(): T {
        return iNetService
    }
}