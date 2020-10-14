package com.zh.kotlin_mvvm.adapter

import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zh.common.base.adapter.BaseQuickAdapter
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.BR
import com.zh.kotlin_mvvm.bean.ListBean

/**
 * @auth xiaohua
 * @time 2020/10/10 - 14:51
 * @desc DataBinding 与 recyclerView适配器 绑定的示例
 */
class ListAdapter : BaseQuickAdapter<ListBean>(R.layout.item_list){

    override val onBindVariableId: Int = BR.bean

    override fun onConvert(holder: BaseDataBindingHolder<ViewDataBinding>, item: ListBean) {

    }
}