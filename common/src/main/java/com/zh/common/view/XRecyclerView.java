package com.zh.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zh.common.base.adapter.BaseZQuickAdapter;
import com.zh.common.view.listener.INetCallbackView;

import java.util.List;

/**
 * @auth xiaohua
 * @time 2021/4/13 - 15:58
 * @desc 自定义RecyclerView，含有：空数据、错误、无网络默认页面
 */
public class XRecyclerView extends FrameLayout implements INetCallbackView {

    private RecyclerView mRecyclerView;
    private XLoadingView mLoadingView;
    private XEmptyView mEmptyView;
    private XErrView mErrView;
    private XNoNetView mNoNetView;
    private OnRefreshListener mOnRefreshListener;
    private BaseQuickAdapter mBaseQuickAdapter;

    public XRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //初始化RecyclerView
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setOverScrollMode(2);
        addView(mRecyclerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //初始化LoadingView
        mLoadingView = new XLoadingView(context);
        mLoadingView.setVisibility(GONE);
        addView(mLoadingView);
        //初始化EmptyView
        mEmptyView = new XEmptyView(context);
        mEmptyView.setVisibility(GONE);
        addView(mEmptyView);
        //初始化ErrView
        mErrView = new XErrView(context);
        mErrView.setVisibility(GONE);
        addView(mErrView);
        //初始化NoNetView
        mNoNetView = new XNoNetView(context);
        mNoNetView.setVisibility(GONE);
        addView(mNoNetView);
        mErrView.setOnRefreshClickListener(() -> {
            if (mOnRefreshListener != null) mOnRefreshListener.onRefreshClick();
        });
        mNoNetView.setOnRefreshClickListener(() -> {
            if (mOnRefreshListener != null) mOnRefreshListener.onRefreshClick();
        });
    }

    //设置新数据
    public void setNewInstance(List dataList) {
        if (mBaseQuickAdapter == null) return;
        if (dataList != null && dataList.size() > 0) {
            mBaseQuickAdapter.setNewInstance(dataList);
        } else {
            mBaseQuickAdapter.setEmptyView(mEmptyView);
        }
    }

    //累计添加数据
    public void addData(List dataList) {
        if (mBaseQuickAdapter == null) return;
        if (dataList != null && dataList.size() > 0) {
            mBaseQuickAdapter.addData(dataList);
        }
        if (mBaseQuickAdapter.getData().size() <= 0) {
            mBaseQuickAdapter.setEmptyView(mEmptyView);
        }
    }

    //设置适配器
    public void setQuickAdapter(BaseQuickAdapter adapter) {
        mBaseQuickAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setZQuickAdapter(BaseZQuickAdapter adapter) {
        mBaseQuickAdapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 设置布局属性
     */
    public void setLayoutManager(RecyclerView.LayoutManager mLayoutManager) {
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onLoadingView(boolean show) {
        if (mBaseQuickAdapter != null) {
            mBaseQuickAdapter.setEmptyView(show ? mLoadingView : null);
        }
    }

    @Override
    public void onFailure(@org.jetbrains.annotations.Nullable String errMsg) {
        if (mBaseQuickAdapter != null) {
            mBaseQuickAdapter.setEmptyView(mErrView);
        }
    }

    @Override
    public void onNoNetWork() {
        if (mBaseQuickAdapter != null) {
            mBaseQuickAdapter.setEmptyView(mNoNetView);
        }
    }

    public interface OnRefreshListener {
        void onRefreshClick();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }
}
