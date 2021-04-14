package com.zh.kotlin_mvvm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.bean.ListBean
import kotlinx.android.synthetic.main.item_list.view.*

/**
 * @auth xiaohua
 * @time 2020/10/10 - 14:51
 * @desc DataBinding 与 recyclerView适配器 绑定的示例
 */
class ListAdapter : BaseQuickAdapter<ListBean, BaseViewHolder>(R.layout.item_list) {

    override fun convert(holder: BaseViewHolder, item: ListBean) {
        holder.itemView.tvTitle.text = item.title
    }
}