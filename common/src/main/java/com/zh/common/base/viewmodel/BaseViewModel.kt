package com.zh.common.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.zh.common.base.model.BaseModel
import com.zh.common.utils.LogUtil

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:02
 * @desc ViewModel基类
 */
open class BaseViewModel<MODEL : BaseModel<*>?>(model: MODEL) : ViewModel(), IBaseViewModel {

    val mModel: MODEL = model

    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {

    }

    override fun onCreate() {

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
        mModel?.onCleared()
    }

    override fun onCleared() {
        super.onCleared()
        LogUtil.d("--okhttp--", "onCleared----")
    }
}

