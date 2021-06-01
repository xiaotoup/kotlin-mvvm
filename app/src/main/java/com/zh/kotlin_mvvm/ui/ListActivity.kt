package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.zh.common.base.BaseActivity
import com.zh.config.ZjConfig
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.adapter.ListAdapter
import com.zh.kotlin_mvvm.bean.ListBean
import com.zh.kotlin_mvvm.mvvm.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*

/**
 * @auth xiaohua
 * @time 2020/10/10 - 14:51
 * @desc DataBinding 与 recyclerView适配器 绑定的示例
 */
@Route(path = ZjConfig.ListActivity)
class ListActivity : BaseActivity<ViewDataBinding, ListViewModel>() {

    override val layoutRes = R.layout.activity_list
    override val viewModel: ListViewModel = ListViewModel()
    override val navigationBarColor: Int = R.color.colorPrimary
    override val statusBarColor: Int = R.color.colorPrimary

    private var listData: MutableList<ListBean> = mutableListOf()
    private val mAdapter by lazy {
        ListAdapter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        for (i in 0..5) {
            listData.add(ListBean(i, "$i item"))
        }
        recyclerView.setQuickAdapter(mAdapter)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            ToastUtils.showShort("$position")
        }
        initData()
    }

    fun initData() {
        for (i in listData.indices) {
            println("$i ${listData[i].title}")
        }
        recyclerView.setNewInstance(listData)
        viewModel.onDoNet(recyclerView, listData)
        /**
         * 高阶函数
         */
        //1、forEach 便利
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        list.forEach(::println)
        val newList = arrayListOf<Int>()
        list.forEach { newList.add(it * 2) }
        newList.forEach(::println)
        //2、map 变换
        list.map { (it * 3).toString() }.forEach(::println)
        list.map { it.toDouble() }.forEach(::println)
        //3、flatMap 对集合的集合进行变换
        val listInt = arrayOf(1..5, 50..55, 100..105)
        //把多个数组集合变成一个数组，并且对数据进行变换
        listInt.flatMap { intRange -> intRange.map { "No.$it" } }.forEach(::println)
        //直接多个数组集合变换成一个结集合
        listInt.flatMap { it }.forEach(::println)
        //4、reduce ，fold，foldRight 倒叙, joinToString转换字符去
        val listNew = arrayOf(1..5, 2..3)
        val nList = listNew.flatMap { it }.forEach(::println)
        //求和 reduce 返回值必须是 acc类型

        refreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                refreshLayout.finishLoadMore(1500)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                listData.clear()
                mAdapter.notifyDataSetChanged()
                viewModel.onDoNet(recyclerView, listData)
                refreshLayout.finishRefresh(1500)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}