package com.zh.common.base.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 多类型布局，适用于类型较少，业务不复杂的场景，便于快速使用。
 * data[T]必须实现[MultiItemEntity]
 *
 * 如果数据类无法实现[MultiItemEntity]，请使用[BaseDelegateMultiAdapter]
 * 如果类型较多，为了方便隔离各类型的业务逻辑，推荐使用[BaseProviderMultiAdapter]
 *
 * @param T : MultiItemEntity
 * @param VH : BaseViewHolder
 * @constructor
 */
abstract class BaseMultiItemQuickAdapter<T : MultiItemEntity>(data: MutableList<T>? = null) :
    BaseMultiItemQuickAdapter<T, BaseDataBindingHolder<ViewDataBinding>>(data) {

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