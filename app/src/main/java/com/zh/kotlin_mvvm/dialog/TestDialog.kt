package com.zh.kotlin_mvvm.dialog

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.zh.common.base.BaseDialogFragment
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.kotlin_mvvm.R
import java.util.*

class TestDialog : BaseDialogFragment<ViewDataBinding, NormalViewModel>() {
    override val layoutRes: Int = R.layout.dialog_test
    override val viewModel: NormalViewModel = NormalViewModel()
    override val onBindVariableId: Int = 0
    override val marginWidth: Int = 30

    override fun initView(savedInstanceState: Bundle?, view: View) {
        val m = Properties()
    }
}