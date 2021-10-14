package com.zh.kotlin_mvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.zh.kotlin_mvvm.R
import kotlinx.android.synthetic.main.activity_motion.*

class MotionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)

        /*botton1.setOnClickListener {
            val sc = ConstraintSet()
            sc.load(this, R.xml.text_motion)
            TransitionManager.beginDelayedTransition(motion)
            sc.applyTo(motion)
        }*/
    }
}