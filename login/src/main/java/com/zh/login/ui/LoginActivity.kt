package com.zh.login.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.utils.LogUtil
import com.zh.common.utils.ScreenUtils
import com.zh.config.ZjConfig
import com.zh.login.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlin.math.abs


@Route(path = ZjConfig.LoginActivity)
class LoginActivity : BaseActivity<ViewDataBinding, NormalViewModel>() {

    override val layoutRes: Int
        get() = R.layout.activity_login

    override val viewModel: NormalViewModel = NormalViewModel()

    override val onBindVariableId: Int
        get() = 0

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        var beforeY = 0
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                //得到滑动后可视子布局的高度
                var sy = appBarLayout.measuredHeight + verticalOffset
                //当高度改变就重新设置子布局的高度
                if (beforeY != abs(verticalOffset)) {
                    var lps: CollapsingToolbarLayout.LayoutParams =
                        changeView.layoutParams as CollapsingToolbarLayout.LayoutParams
                    lps.height = sy
                    changeView.layoutParams = lps
                }
                beforeY = abs(verticalOffset)
            }
        })
    }
}