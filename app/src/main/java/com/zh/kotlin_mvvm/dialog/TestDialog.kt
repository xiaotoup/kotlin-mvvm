package com.zh.kotlin_mvvm.dialog

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.zh.common.base.BaseDialogFragment
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.kotlin_mvvm.R

class TestDialog(
    override val layoutRes: Int = R.layout.dialog_test
) : BaseDialogFragment<ViewDataBinding>() {

    override var marginHorizontal = 30

    override fun initView(savedInstanceState: Bundle?, view: View) {

    }
}