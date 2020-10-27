package com.zh.login.ui

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.zh.common.base.BaseActivity
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.common.utils.LogUtil
import com.zh.config.ZjConfig
import com.zh.login.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*


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
        var afterY = 0
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                LogUtil.e("verticalOffset  $verticalOffset")
                LogUtil.e("toolbarLayout  ${toolbarLayout.measuredHeight}")
                //当高度改变就重新设置子布局的高度
                /*var lps: CollapsingToolbarLayout.LayoutParams =
                    changeView.layoutParams as CollapsingToolbarLayout.LayoutParams
                //得到滑动后可视子布局的高度
                lps.height = appBarLayout.measuredHeight - abs(verticalOffset)
                changeView.layoutParams = lps*/
                val scale =
                    (changeView.measuredHeight + verticalOffset).toFloat() / changeView.measuredHeight
                changeView.scaleX = scale
                changeView.scaleY = scale
            }
        })
    }
}