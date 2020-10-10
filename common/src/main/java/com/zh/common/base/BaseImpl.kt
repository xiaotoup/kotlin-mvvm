package com.zh.common.base

import android.content.Context
import io.reactivex.disposables.CompositeDisposable

interface BaseImpl {
    val context: Context
    val disposable: CompositeDisposable
}