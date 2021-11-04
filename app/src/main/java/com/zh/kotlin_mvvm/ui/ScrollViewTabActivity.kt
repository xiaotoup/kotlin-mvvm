package com.zh.kotlin_mvvm.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.databinding.ViewDataBinding
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.zh.common.base.BaseActivity
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.bean.TabEntity
import kotlinx.android.synthetic.main.activity_scroll_view_tab.*

class ScrollViewTabActivity(override val layoutRes: Int = R.layout.activity_scroll_view_tab) :
    BaseActivity<ViewDataBinding>() {

    private var scrollviewFlag = false //标记是否是scrollview引起的滑动

    @SuppressLint("ClickableViewAccessibility", "NewApi")
    override fun initView(savedInstanceState: Bundle?) {
        val tabList = ArrayList<CustomTabEntity>()
        tabList.add(TabEntity("tab1"))
        tabList.add(TabEntity("tab2"))
        tabList.add(TabEntity("tab3"))
        cTabLayout.setTabData(tabList)

        cTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> scrollView.smoothScrollTo(0, test1.top)
                    1 -> scrollView.smoothScrollTo(0, test2.top)
                    2 -> scrollView.smoothScrollTo(0, test3.top)
                }
                scrollviewFlag = false
            }

            override fun onTabReselect(position: Int) {
            }
        })

        scrollView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                scrollviewFlag = true
            }
            false
        }
        scrollView.setOnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollviewFlag) {
                val isBottom =
                    scrollView.getChildAt(0).height == scrollY + scrollView.height - scrollView.paddingTop - scrollView.paddingBottom
                when {
                    (scrollY < test2.top) and (cTabLayout.currentTab != 0) -> {
                        cTabLayout.currentTab = 0
                    }
                    (scrollY >= test2.top) and (scrollY < test3.top) and (cTabLayout.currentTab != 1) -> {
                        cTabLayout.currentTab = 1
                    }
                    ((scrollY >= test3.top) or isBottom) and (cTabLayout.currentTab != 2) -> {
                        cTabLayout.currentTab = 2
                    }
                }
            }
        }
    }
}