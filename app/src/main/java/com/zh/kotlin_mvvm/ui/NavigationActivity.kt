package com.zh.kotlin_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.zh.kotlin_mvvm.R
import com.zh.kotlin_mvvm.navigation.YFragment1
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }
}