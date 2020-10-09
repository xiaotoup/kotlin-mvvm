package com.zh.common.base.viewmodel

import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zh.common.base.model.BaseModel
import com.zh.common.utils.LogUtil
import io.reactivex.disposables.Disposable

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:02
 * @desc ViewModel基类
 */
open class BaseViewModel<MODEL : BaseModel<*>?>(model: MODEL) : ViewModel(), IBaseViewModel,
    Consumer<Disposable> {

    var mModel: MODEL? = null

    init {
        mModel = model
    }

    //绑定网络请求
    override fun accept(disposable: Disposable) {
        mModel?.addSubscribe(disposable)
    }

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
        LogUtil.d("--okHttp--", "viewModel onAny")
    }

    override fun onCreate() {
        LogUtil.d("--okHttp--", "viewModel onCreate")
    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}

