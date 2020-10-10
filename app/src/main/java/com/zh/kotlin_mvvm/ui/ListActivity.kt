package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.zh.common.base.BaseActivity
import com.zh.common.base.model.NormalModel
import com.zh.common.base.viewmodel.NormalViewModel
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.adapter.ListAdapter
import com.zh.kotlin_mvvm.bean.ListBean
import kotlinx.android.synthetic.main.activity_list.*

/**
 * @auth xiaohua
 * @time 2020/10/10 - 14:51
 * @desc DataBinding 与 recyclerView适配器 绑定的示例
 */
@Route(path = ZjConfig.ListActivity)
class ListActivity : BaseActivity<ViewDataBinding, NormalViewModel>() {

    override val layoutRes = R.layout.activity_list
    override fun viewModel(): NormalViewModel = NormalViewModel(NormalModel())
    override val onBindVariableId = 0

    private var list: MutableList<ListBean> = mutableListOf<ListBean>()
    private val mAdapter by lazy {
        ListAdapter(list)
    }

    override fun initView(savedInstanceState: Bundle?) {
        for (i in 0..20) {
            list.add(ListBean(i,"$i item"))
        }
        recyclerView.adapter = mAdapter
    }

    override fun initData() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.unbind()
    }
}