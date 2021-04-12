package com.zh.common.base

import io.reactivex.disposables.CompositeDisposable

interface BaseImpl {
    val disposable: CompositeDisposable
}