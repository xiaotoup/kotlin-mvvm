package com.zh.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zh.common.R;
import com.zh.common.base.adapter.BaseZQuickAdapter;
import com.zh.common.view.listener.INetCallbackView;

import java.util.List;

/**
 * @auth xiaohua
 * @time 2021/4/13 - 15:58
 * @desc 自定义RecyclerView，含有：空数据、错误、无网络默认页面
 */
public class XRecyclerView extends FrameLayout implements INetCallbackView {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private XLoadingView mLoadingView;
    private XEmptyView mEmptyView;
    private XErrView mErrView;
    private XNoNetView mNoNetView;
    private OnRefreshListener mOnRefreshListener;
    private BaseQuickAdapter mBaseQuickAdapter;
    private boolean isOpenAnimator = true;
    private int animationId = R.anim.layout_animation_fall_down;

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
        mContext = context;
        //初始化RecyclerView
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setOverScrollMode(2);
        openItemDefaultAnimator();
        addView(mRecyclerView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //初始化LoadingView
        mLoadingView = new XLoadingView(context);
        //初始化EmptyView
        mEmptyView = new XEmptyView(context);
        //初始化ErrView
        mErrView = new XErrView(context);
        //初始化NoNetView
        mNoNetView = new XNoNetView(context);
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
            openItemDefaultAnimator();
            mBaseQuickAdapter.setNewInstance(dataList);
        }
        if (mBaseQuickAdapter.getData().size() <= 0) {
            setOnEmpty();
        }
    }

    //累计添加数据
    public void addData(List dataList) {
        if (mBaseQuickAdapter == null) return;
        if (dataList != null && dataList.size() > 0) {
            openItemDefaultAnimator();
            mBaseQuickAdapter.addData(dataList);
        }
        if (mBaseQuickAdapter.getData().size() <= 0) {
            setOnEmpty();
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

    /**
     * 开启 RecyclerView 的 layoutAnimation动画
     */
    public void openItemDefaultAnimator() {
        if (isOpenAnimator) {
            mRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(mContext, animationId));
        }
    }

    /**
     * 添加自定义动画
     */
    public void openItemAnimator(int animationId) {
        this.animationId = animationId;
        openItemDefaultAnimator();
    }

    /**
     * 清除动画
     */
    public void closeItemAnimator() {
        isOpenAnimator = false;
        mRecyclerView.setLayoutAnimation(null);
    }

    /**
     * 开启 RecyclerView 滑动
     */
    public void openNestedScrolling() {
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setFocusableInTouchMode(true);
    }

    /**
     * 禁止 RecyclerView 滑动
     */
    public void closeNestedScrolling() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusableInTouchMode(false);
    }

    /********************************** Loading面设置 start **********************************/
    /**
     * 设置Loading页面文字和图标
     */
    public void setLoadingViewText(String desc) {
        if (mLoadingView != null) {
            mLoadingView.setTextDesc(desc);
        }
    }

    /**
     * 设置Loading页面文字大小
     */
    public void setLoadingTextSize(float desc) {
        if (mLoadingView != null) {
            mLoadingView.setTextSize(desc);
        }
    }

    /**
     * 设置Loading页面文字颜色
     */
    public void setLoadingTextColor(int color) {
        if (mLoadingView != null) {
            mLoadingView.setTextColor(color);
        }
    }

    /********************************** 空页面设置 start **********************************/
    /**
     * 设置空页面文字和图标
     */
    public void setEmptyViewTextImage(String desc, int resId) {
        if (mEmptyView != null) {
            mEmptyView.setTextDesc(desc);
            mEmptyView.setImage(resId);
        }
    }

    /**
     * 设置空页面文字
     */
    public void setEmptyViewText(String desc) {
        if (mEmptyView != null) {
            mEmptyView.setTextDesc(desc);
        }
    }

    /**
     * 设置空页面文字大小
     */
    public void setEmptyTextSize(float desc) {
        if (mEmptyView != null) {
            mEmptyView.setTextSize(desc);
        }
    }

    /**
     * 设置空页面文字颜色
     */
    public void setEmptyTextColor(int color) {
        if (mEmptyView != null) {
            mEmptyView.setTextColor(color);
        }
    }

    /**
     * 设置空页面图标
     */
    public void setEmptyViewImage(int resId) {
        if (mEmptyView != null) {
            mEmptyView.setImage(resId);
        }
    }

    /**
     * 设置空页面图片大小
     */
    public void setEmptyImageSize(int width, int height) {
        if (mEmptyView != null) {
            mEmptyView.setImageSize(width, height);
        }
    }

    /********************************** 空页面设置 end **********************************/
    /**
     * 设置错误页面文字和图标
     */
    public void setErrViewTextImage(String desc, int resId) {
        if (mErrView != null) {
            mErrView.setTextDesc(desc);
            mErrView.setImage(resId);
        }
    }

    /**
     * 设置错误页面文字
     */
    public void setErrViewText(String desc) {
        if (mErrView != null) {
            mErrView.setTextDesc(desc);
        }
    }

    /**
     * 设置错误页面文字大小
     */
    public void setErrTextSize(float desc) {
        if (mErrView != null) {
            mErrView.setTextSize(desc);
        }
    }

    /**
     * 设置错误页面文字颜色
     */
    public void setErrTextColor(int color) {
        if (mErrView != null) {
            mErrView.setTextColor(color);
        }
    }

    /**
     * 设置错误页面图标
     */
    public void setErrViewImage(int resId) {
        if (mErrView != null) {
            mErrView.setImage(resId);
        }
    }

    /**
     * 设置错误页面图片大小
     */
    public void seErrImageSize(int width, int height) {
        if (mErrView != null) {
            mErrView.setImageSize(width, height);
        }
    }

    /********************************** 错误页面设置 end **********************************/

    /**
     * 设置无网络页面文字和图标
     */
    public void setNoNetViewTextImage(String desc, int resId) {
        if (mNoNetView != null) {
            mNoNetView.setTextDesc(desc);
            mNoNetView.setImage(resId);
        }
    }

    /**
     * 设置无网络页面文字
     */
    public void setNoNetViewText(String desc) {
        if (mNoNetView != null) {
            mNoNetView.setTextDesc(desc);
        }
    }

    /**
     * 设置无网络页面文字大小
     */
    public void setNoNetTextSize(float desc) {
        if (mNoNetView != null) {
            mNoNetView.setTextSize(desc);
        }
    }

    /**
     * 设置无网络页面文字颜色
     */
    public void setNoNetTextColor(int color) {
        if (mNoNetView != null) {
            mNoNetView.setTextColor(color);
        }
    }

    /**
     * 设置无网络页面图标
     */
    public void setNoNetViewImage(int resId) {
        if (mNoNetView != null) {
            mNoNetView.setImage(resId);
        }
    }

    /**
     * 设置无网络页面图片大小
     */
    public void seNoNetImageSize(int width, int height) {
        if (mNoNetView != null) {
            mNoNetView.setImageSize(width, height);
        }
    }

    /********************************** 自定义页面设置 end **********************************/

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onLoadingView(boolean show) {
        if (mBaseQuickAdapter != null) {
            if (show) {
                mRecyclerView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
                mBaseQuickAdapter.setEmptyView(mLoadingView);
            } else {
                mRecyclerView.getLayoutParams().height = LayoutParams.WRAP_CONTENT;
                mBaseQuickAdapter.removeEmptyView();
            }
        }
    }

    @Override
    public void onFailure(String errMsg) {
        if (mBaseQuickAdapter != null) {
            mRecyclerView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
            mBaseQuickAdapter.setEmptyView(mErrView);
        }
    }

    @Override
    public void onNoNetWork() {
        if (mBaseQuickAdapter != null) {
            mRecyclerView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
            mBaseQuickAdapter.setEmptyView(mNoNetView);
        }
    }

    public void setOnEmpty() {
        mRecyclerView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
        mBaseQuickAdapter.setEmptyView(mEmptyView);
    }

    public interface OnRefreshListener {
        void onRefreshClick();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        closeItemAnimator();
        if (mBaseQuickAdapter != null) {
            mBaseQuickAdapter.removeEmptyView();
        }
    }
}
