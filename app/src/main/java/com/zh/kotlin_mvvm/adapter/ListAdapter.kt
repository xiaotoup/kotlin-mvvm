package com.zh.kotlin_mvvm.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.zh.kotlin_mvvm.BR
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.bean.ListBean

/**
 * @auth xiaohua
 * @time 2020/10/10 - 14:51
 * @desc DataBinding 与 recyclerView适配器 绑定的示例
 */
class ListAdapter(data: MutableList<ListBean>) :
    BaseQuickAdapter<ListBean, BaseDataBindingHolder<ViewDataBinding>>(R.layout.item_list, data) {

    private var binding: ViewDataBinding? = null

    override fun onItemViewHolderCreated(viewHolder: BaseDataBindingHolder<ViewDataBinding>, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseDataBindingHolder<ViewDataBinding>, item: ListBean) {
        binding = holder.dataBinding
        binding?.setVariable(BR.bean, item)
        binding?.executePendingBindings()
    }

    fun unbind(){
        binding?.let { it.unbind() }
    }
}