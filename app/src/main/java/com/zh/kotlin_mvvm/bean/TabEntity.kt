package com.zh.kotlin_mvvm.bean

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity(val title: String) : CustomTabEntity {
    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return 0
    }

    override fun getTabUnselectedIcon(): Int {
        return 0
    }
}