package com.zh.common.base.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

abstract class BaseZQuickAdapter<T>(layoutRes: Int) :
    BaseQuickAdapter<T, BaseDataBindingHolder<ViewDataBinding>>(layoutRes) {

    private var binding: ViewDataBinding? = null

    override fun onItemViewHolderCreated(
        viewHolder: BaseDataBindingHolder<ViewDataBinding>,
        viewType: Int
    ) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseDataBindingHolder<ViewDataBinding>, item: T) {
        binding = holder.dataBinding
        binding?.let {
            it.setVariable(onBindVariableId, item)
            it.executePendingBindings()
        }
        onConvert(holder, item)
    }

    abstract val onBindVariableId: Int
    abstract fun onConvert(holder: BaseDataBindingHolder<ViewDataBinding>, item: T)

    open fun unbind() {
        binding?.let { it.unbind() }
    }
}