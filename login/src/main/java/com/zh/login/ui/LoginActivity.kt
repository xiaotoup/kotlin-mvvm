package com.zh.login.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.BaseViewModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.config.ZjConfig
import com.zh.login.R
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Math.abs


@Route(path = ZjConfig.LoginActivity)
class LoginActivity : BaseActivity<ViewDataBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_login

    override val viewModel: BaseViewModel
        get() = NormalViewModel()

    override val viewModelId: Int
        get() = 0

    override fun initView(savedInstanceState: Bundle?) {
        startActivity(Intent(this, sdActivity::class.java))
        var afterY = 0
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                LogUtils.eTag("verticalOffset  $verticalOffset")
                LogUtils.eTag("toolbarLayout  ${toolbarLayout.measuredHeight}")
                //当高度改变就重新设置子布局的高度
                var lps: CollapsingToolbarLayout.LayoutParams =
                    changeView.layoutParams as CollapsingToolbarLayout.LayoutParams
                //得到滑动后可视子布局的高度
                lps.height = appBarLayout.measuredHeight - abs(verticalOffset)
                changeView.layoutParams = lps
                val scale =
                    (changeView.measuredHeight + verticalOffset).toFloat() / changeView.measuredHeight
                changeView.scaleX = scale
                changeView.scaleY = scale
            }
        })
    }
}