package com.zh.common.view.dialog

import android.content.Context
import java.lang.ref.WeakReference

class MyRunnable(context: Context?, loadingDialog: LoadingDialog?) :
    Runnable {
    private val mContext: WeakReference<Context?> = WeakReference(context)
    private val dialog: WeakReference<LoadingDialog?> = WeakReference(loadingDialog)
    override fun run() {
        if (mContext.get() != null) {
            try {
                if (dialog.get() != null) {
                    dialog.get()?.dismiss()
                }
            } catch (e: Exception) {
            }
        }
    }
}