package com.zh.common.base.viewmodel

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.LogUtils
import com.zh.common.base.BaseApplication
import com.zh.common.base.model.BaseModel

/**
 * @auth xiaohua
 * @time 2020/10/8 - 10:02
 * @desc ViewModel基类
 */
open class BaseViewModel<MODEL : BaseModel<*>>(model: MODEL) :
    AndroidViewModel(BaseApplication.getApplication()), IBaseViewModel {

    val mModel: MODEL = model
    val mAppContext: Context = getApplication<BaseApplication>().applicationContext

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
        LogUtils.dTag("--okhttp--", "onCleared----")
    }
}

