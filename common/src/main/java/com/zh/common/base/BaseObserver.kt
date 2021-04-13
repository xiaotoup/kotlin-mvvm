package com.zh.common.base

import android.content.Context
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.zh.common.base.bean.BaseResponse
import com.zh.common.exception.ApiException
import com.zh.common.exception.ERROR
import com.zh.common.view.dialog.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 自定义Subscriber
 */
abstract class BaseObserver<T> : Observer<T> {
    private val context: Context = ActivityUtils.getTopActivity()
    private var loadingDialog: LoadingDialog? = null
    private var isShowLoading = false //是否显示加载进度对话框
    private var disposable: Disposable? = null

    constructor(isShowLoading: Boolean) {
        this.isShowLoading = isShowLoading
        if (isShowLoading) getLoadingDialog()
    }

    override fun onSubscribe(d: Disposable) {
        LogUtils.d("ThomasDebug", "BaseObserver : Http is start")
        disposable = d

        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("网络异常")
            onComplete() //一定要手动调用
        }

        // 显示进度条
        if (isShowLoading) showLoading()
    }

    override fun onNext(response: T) {
        val result: BaseResponse<T> = response as BaseResponse<T>
        onISuccess(result.msg, response)
    }

    override fun onError(e: Throwable) {
        LogUtils.d("BaseObserver", "onError : " + e.message)
        if (e is ApiException) {
            onIError(e)
        } else {
            onIError(ApiException(e, ERROR.UNKNOWN))
        }

        //关闭等待进度条
        if (isShowLoading) dismissLoading()
    }

    override fun onComplete() {
        LogUtils.d("BaseObserver", "onCompleted : Http is complete")

        //关闭等待进度条
        if (isShowLoading) dismissLoading()
    }


    protected abstract fun onISuccess(message: String, response: T)
    protected abstract fun onIError(e: ApiException)

    fun getDisposable() = disposable

    private fun getLoadingDialog(){
        loadingDialog ?: also { loadingDialog = LoadingDialog(context) }
    }

    /**
     * 显示加载dialog
     */
    private fun showLoading() {
        getLoadingDialog()
        loadingDialog?.show()
    }

    /**
     * 结束dialog
     */
    private fun dismissLoading() {
        loadingDialog?.let { if (it.isShowing) it.dismiss() }
    }
}