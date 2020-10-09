package com.zh.common.immersion

/**
 * ImmersionBar接口
 */
interface ImmersionOwner {
    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy before view.
     */
    fun onLazyBeforeView()

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy after view.
     */
    fun onLazyAfterView()

    /**
     * 用户可见时候调用
     * On visible.
     */
    fun onVisible()

    /**
     * 用户不可见时候调用
     * On invisible.
     */
    fun onInvisible()

    /**
     * 初始化沉浸式代码
     * Init immersion bar.
     */
    fun initImmersionBar()

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    fun immersionBarEnabled(): Boolean
}