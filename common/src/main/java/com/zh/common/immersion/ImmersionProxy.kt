package com.zh.common.immersion

import android.content.res.Configuration
import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Fragment快速实现沉浸式的代理类
 */
class ImmersionProxy(
    /**
     * 要操作的Fragment对象
     */
    private var mFragment: RxFragment?
) {

    /**
     * 沉浸式实现接口
     */
    private var mImmersionOwner: ImmersionOwner? = null

    /**
     * Fragment的view是否已经初始化完成
     */
    private var mIsActivityCreated = false

    /**
     * 懒加载，是否已经在view初始化完成之前调用
     */
    private var mIsLazyAfterView = false

    /**
     * 懒加载，是否已经在view初始化完成之后调用
     */
    private var mIsLazyBeforeView = false

    fun onCreate(savedInstanceState: Bundle?) {
        if (mFragment != null && mFragment!!.userVisibleHint) {
            if (!mIsLazyBeforeView) {
                mImmersionOwner!!.onLazyBeforeView()
                mIsLazyBeforeView = true
            }
        }
    }

    fun onActivityCreated(savedInstanceState: Bundle?) {
        mIsActivityCreated = true
        if (mFragment != null && mFragment!!.userVisibleHint) {
            if (mImmersionOwner!!.immersionBarEnabled()) {
                mImmersionOwner!!.initImmersionBar()
            }
            if (!mIsLazyAfterView) {
                mImmersionOwner!!.onLazyAfterView()
                mIsLazyAfterView = true
            }
        }
    }

    fun onResume() {
        if (mFragment != null && mFragment!!.userVisibleHint) {
            mImmersionOwner!!.onVisible()
        }
    }

    fun onPause() {
        if (mFragment != null) {
            mImmersionOwner!!.onInvisible()
        }
    }

    fun onDestroy() {
        mFragment = null
        mImmersionOwner = null
    }

    fun onConfigurationChanged(newConfig: Configuration?) {
        if (mFragment != null && mFragment!!.userVisibleHint) {
            if (mImmersionOwner!!.immersionBarEnabled()) {
                mImmersionOwner!!.initImmersionBar()
            }
            mImmersionOwner!!.onVisible()
        }
    }

    fun onHiddenChanged(hidden: Boolean) {
        if (mFragment != null) {
            mFragment!!.userVisibleHint = !hidden
        }
    }

    /**
     * 是否已经对用户可见
     * Is user visible hint boolean.
     *
     * @return the boolean
     */
    var isUserVisibleHint: Boolean
        get() = if (mFragment != null) {
            mFragment!!.userVisibleHint
        } else {
            false
        }
        set(isVisibleToUser) {
            if (mFragment != null) {
                if (mFragment!!.userVisibleHint) {
                    if (!mIsLazyBeforeView) {
                        mImmersionOwner!!.onLazyBeforeView()
                        mIsLazyBeforeView = true
                    }
                    if (mIsActivityCreated) {
                        if (mFragment!!.userVisibleHint) {
                            if (mImmersionOwner!!.immersionBarEnabled()) {
                                mImmersionOwner!!.initImmersionBar()
                            }
                            if (!mIsLazyAfterView) {
                                mImmersionOwner!!.onLazyAfterView()
                                mIsLazyAfterView = true
                            }
                            mImmersionOwner!!.onVisible()
                        }
                    }
                } else {
                    if (mIsActivityCreated) {
                        mImmersionOwner!!.onInvisible()
                    }
                }
            }
        }

    init {
        if (mFragment is ImmersionOwner) {
            mImmersionOwner = mFragment as ImmersionOwner?
        } else {
            throw IllegalArgumentException("Fragment请实现ImmersionOwner接口")
        }
    }
}