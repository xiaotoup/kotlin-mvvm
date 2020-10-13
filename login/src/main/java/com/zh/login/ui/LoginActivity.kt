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
        var sTop = 0
        scrollView.post(Runnable {
            sTop = scrollView.top
        })
        var viewHeight = 0
        changeView.post(Runnable {
            viewHeight = changeView.measuredHeight
        })
        var beforeY = sTop
        appBarLayout.setExpanded(true)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {

                var sy = appBarLayout.measuredHeight + verticalOffset    //距离原来的位置高度
                LogUtil.e("sy  " + sy)
                LogUtil.e("changeView  " + changeView?.top)
                LogUtil.e("scrollView=  " + (sTop - scrollView.top))
                LogUtil.e("verticalOffset  " + verticalOffset)

                if (beforeY != scrollView.top) {
                    var lps: CollapsingToolbarLayout.LayoutParams =
                        changeView.layoutParams as CollapsingToolbarLayout.LayoutParams
                    lps.height = sy
                    changeView.layoutParams = lps
                }
                beforeY = (scrollView.top)
            }
        })
    }
}