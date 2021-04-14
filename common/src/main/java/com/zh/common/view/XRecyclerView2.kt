package com.zh.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zh.common.base.adapter.BaseZQuickAdapter
import com.zh.common.view.listener.OnRefreshClickListener

/**
 * @auth xiaohua
 * @time 2021/4/13 - 15:58
 * @desc 自定义RecyclerView，含有：空数据、错误、无网络默认页面
 */
class XRecyclerView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var mRecyclerView = RecyclerView(context)
    private var mLoadingView = XLoadingView(context)
    private var mEmptyView = XEmptyView(context)
    private var mErrView = XErrView(context)
    private var mNoNetView = XNoNetView(context)
    private var mOnRefreshListener: OnRefreshListener? = null
    private var mBaseQuickAdapter: BaseQuickAdapter<*, *>? = null

    init {
        addView(mRecyclerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        addView(mLoadingView)
        addView(mEmptyView)
        addView(mErrView)
        addView(mNoNetView)
        mRecyclerView.apply {
            //默认纵向布局
            layoutManager = LinearLayoutManager(context)
            //去掉拉动阴影
            overScrollMode = 2
        }
        mLoadingView.apply {
            visibility = View.GONE
        }
        mEmptyView.apply {
            visibility = View.GONE
        }
        mErrView.apply {
            visibility = View.GONE
            setOnRefreshClickListener(object : OnRefreshClickListener {
                override fun onRefreshClick() {
                    mOnRefreshListener?.onRefreshClick()
                }
            })
        }
        mNoNetView.apply {
            visibility = View.GONE
            setOnRefreshClickListener(object : OnRefreshClickListener {
                override fun onRefreshClick() {
                    mOnRefreshListener?.onRefreshClick()
                }
            })
        }
    }

    //设置新数据
    fun <T> setNewInstance(dataList: MutableList<T>?) {
        dataList?.let {
//            mBaseQuickAdapter?.setNewInstance(it)
        } ?: mBaseQuickAdapter?.setEmptyView(mEmptyView)
    }

    //设置适配器
    fun setQuickAdapter(adapter: BaseQuickAdapter<*, *>) {
        mBaseQuickAdapter = adapter
        mRecyclerView.adapter = adapter
    }

    fun setZQuickAdapter(adapter: BaseZQuickAdapter<*>) {
        mBaseQuickAdapter = adapter
        mRecyclerView.adapter = adapter
    }

    /**
     * 设置布局属性
     */
    fun setLayoutManager(mLayoutManager: RecyclerView.LayoutManager) {
        mRecyclerView.layoutManager = mLayoutManager
    }

    fun getRecyclerView() = mRecyclerView

    interface OnRefreshListener {
        fun onRefreshClick()
    }

    fun setOnRefreshListener(listener: OnRefreshListener) {
        mOnRefreshListener = listener
    }
}