package com.zh.common.schedulers

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler

/**
 * @author: zxh
 * @date: 2019/5/24
 * @description:
 */
interface BaseSchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
    fun <T> applySchedulers(): ObservableTransformer<T, T>
}