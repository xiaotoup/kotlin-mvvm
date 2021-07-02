package com.zh.kotlin_mvvm.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.SizeUtils
import com.zh.kotlin_mvvm.R
import kotlinx.android.synthetic.main.activity_number_picker.*

@RequiresApi(Build.VERSION_CODES.Q)
class NumberPickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_picker)

        hourpicker.minValue = 0
        hourpicker.maxValue = 20
        hourpicker.textColor = Color.BLUE//文字颜色
        hourpicker.textSize = SizeUtils.sp2px(18f).toFloat()//文字大小
        hourpicker.selectionDividerHeight = SizeUtils.dp2px(1f)//分割线高度
        hourpicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS//关闭编辑模式
//        hourpicker.wrapSelectorWheel = false//是否循环
        hourpicker.value = 5//当前需要选中那个位置
//        setPickerMargin(hourpicker,0, 20, 0, 20)
//        setDividerColor(hourpicker)
        hourpicker.isInEditMode
        hourpicker.post {
            setDividerColor(hourpicker)
        }
    }

    /**
     * 设置picker之间的间距
     */
    private fun setPickerMargin(
        picker: NumberPicker,
        left: Int = 0,
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0
    ) {
        val lp = picker.layoutParams as LinearLayout.LayoutParams
        lp.setMargins(left, top, right, bottom)
    }

    private fun setDividerColor(picker: NumberPicker, color: Int = Color.RED) {
        try {
            //获取私有方法
            var field = picker.javaClass.getDeclaredField("mSelectionDivider")
            field.let {
                it.isAccessible = true
                it.set(picker, ColorDrawable(color))
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}