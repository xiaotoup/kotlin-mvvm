package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.config.fragment.PictureFragment
import com.zh.kotlin_mvvm.R
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity(
    override val layoutRes: Int = R.layout.activity_picture,
    override val viewModel: BaseViewModel = NormalViewModel()
) : BaseActivity<ViewDataBinding>() {

    private lateinit var pictureFragment: PictureFragment

    override fun initView(savedInstanceState: Bundle?) {
        pictureFragment = supportFragmentManager.findFragmentByTag("pictureF") as PictureFragment

        button9.setOnClickListener {
            pictureFragment.showSelectDialog()
        }
    }
}