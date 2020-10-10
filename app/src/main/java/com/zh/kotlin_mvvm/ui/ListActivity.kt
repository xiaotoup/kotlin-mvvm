package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.zh.common.base.BaseActivity
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
    override val viewModel: NormalViewModel = NormalViewModel()
    override val onBindVariableId = 0

    private var list: MutableList<ListBean> = mutableListOf<ListBean>()
    private val mAdapter by lazy {
        ListAdapter(list)
    }

    override fun initView(savedInstanceState: Bundle?) {
        for (i in 0..20) {
            list.add(ListBean(i, "$i item"))
        }
        recyclerView.adapter = mAdapter
    }

    override fun initData() {
        for (i in list.indices) {
            println("$i ${list[i].title}")
        }
        refreshLayout.autoRefresh()
        refreshLayout.autoLoadMore()
        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore(2000)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                refreshLayout.finishRefresh(2000)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.unbind()
    }
}