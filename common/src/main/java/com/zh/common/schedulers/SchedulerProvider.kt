package com.zh.common.schedulers

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: zxh
 * @date: 2019/5/24
 * @description: Rxjava线程切换
 */
class SchedulerProvider  // Prevent direct instantiation.
private constructor() : BaseSchedulerProvider {
    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable: Observable<T> ->
            observable.subscribeOn(io())
                .observeOn(ui())
        }
    }

    companion object {

        private var INSTANCE: SchedulerProvider? = null

        @get:Synchronized
        val instance: SchedulerProvider
            get() {
                if (INSTANCE == null) {
                    INSTANCE = SchedulerProvider()
                }
                return INSTANCE!!
            }
    }
}