package com.zh.common.base.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.zh.common.base.BaseApplication
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
 * @time 2020/10/8 - 10:02
 * @desc ViewModel基类
 */
open class BaseViewModel : AndroidViewModel(BaseApplication.getApplication()) {

    var pageIndex = 1
    var pageSize = 10
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("StaticFieldLeak")
    val mAppContext: Context = getApplication<BaseApplication>().applicationContext

    //添加网络请求到CompositeDisposable
    private fun addSubscribe(disposable: Disposable) {
        mCompositeDisposable.also {
            Log.e("--okhttp--", "disposable is add")
            it.add(disposable)
        }
    }

    override fun onCleared() {
        //解除网络请求
        mCompositeDisposable.also {
            Log.e("--okhttp--", "disposable is clear")
            mCompositeDisposable.clear()
        }
    }

    /**
     * 同步调用
     */
    fun <T> getINetService(service: Class<*>): T {
        return ClientModule.instance.netRequest(service)
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

