package com.zh.kotlin_mvvm.ui

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.zh.common.base.BaseActivity
import com.zh.config.ZjConfig
import com.zh.found.FoundFragment
import com.zh.home.HomeFragment
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.databinding.ActivityMainBinding
import com.zh.mine.MineFragment
import com.zh.new.NewFragment
import kotlinx.android.synthetic.main.activity_main.*

@Route(path = ZjConfig.MainActivity)
class MainActivity(
    override val layoutRes: Int = R.layout.activity_main
) : BaseActivity<ActivityMainBinding>() {

    private var currentIndex = 0

    private var mHomeFragment: HomeFragment? = null
    private var mFoundFragment: FoundFragment? = null
    private var mNewFragment: NewFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun initView(savedInstanceState: Bundle?) {
        initFragment(0)
        clickTab()
    }

    /**
     * 添加fragment
     */
    private fun initFragment(position: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        selectText(position)
        hideAllFragment(beginTransaction)
        when (position) {
            0 -> {
                mHomeFragment ?: also {
                    mHomeFragment = HomeFragment()
                    mHomeFragment?.let { beginTransaction.add(R.id.container, it) }
                }
                mHomeFragment?.let { beginTransaction.show(it) }
            }
            1 -> {
                mFoundFragment ?: also {
                    mFoundFragment = FoundFragment()
                    mFoundFragment?.let { beginTransaction.add(R.id.container, it) }
                }
                mFoundFragment?.let { beginTransaction.show(it) }
            }
            2 -> {
                mNewFragment ?: also {
                    mNewFragment = NewFragment()
                    mNewFragment?.let { beginTransaction.add(R.id.container, it) }
                }
                mNewFragment?.let { beginTransaction.show(it) }
            }
            3 -> {
                mMineFragment ?: also {
                    mMineFragment = MineFragment()
                    mMineFragment?.let { beginTransaction.add(R.id.container, it) }
                }
                mMineFragment?.let { beginTransaction.show(it) }
            }
            else -> {
                LogUtils.e("加载菜单出错!")
            }
        }
        beginTransaction.commitAllowingStateLoss()
        currentIndex = position
    }

    /**
     * 隐藏已经加载的Fragment
     */
    private fun hideAllFragment(beginTransaction: FragmentTransaction) {
        mHomeFragment?.let { beginTransaction.hide(it) }
        mFoundFragment?.let { beginTransaction.hide(it) }
        mNewFragment?.let { beginTransaction.hide(it) }
        mMineFragment?.let { beginTransaction.hide(it) }
    }

    /**
     * 设置文字选中
     */
    private fun selectText(position: Int) {
        for (i in 0 until llTab.childCount) {
            llTab.getChildAt(i).isSelected = position == i
        }
    }

    /**
     * 点击事件
     */
    private fun clickTab() {
        tabHome.setOnClickListener { initFragment(0) }
        tabFound.setOnClickListener { initFragment(1) }
        tabNew.setOnClickListener { initFragment(2) }
        tabMine.setOnClickListener { initFragment(3) }
    }
}
