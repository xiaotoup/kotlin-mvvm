package com.zh.common.base.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseBinderAdapter
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 多类型布局，通过代理类的方式，返回布局 id 和 item 类型；
 * 适用于:
 * 1、实体类不方便扩展，此Adapter的数据类型可以是任意类型，只需要在[BaseMultiTypeDelegate.getItemType]中返回对应类型
 * 2、item 类型较少
 * 如果类型较多，为了方便隔离各类型的业务逻辑，推荐使用[BaseBinderAdapter]
 *
 * @param T
 * @param VH : BaseViewHolder
 * @property mMultiTypeDelegate BaseMultiTypeDelegate<T>?
 * @constructor
 */
abstract class BaseZDelegateMultiAdapter<T>(data: MutableList<T>? = null) :
    BaseDelegateMultiAdapter<T, BaseDataBindingHolder<ViewDataBinding>>(data) {
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