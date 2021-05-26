package com.zh.common.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.SizeUtils
import com.luck.picture.lib.tools.DoubleUtils
import com.zh.common.R

/**
 * Created by pc on 2018/4/21.
 * 仿ios弹框
 */
class BottomDialog(context: Context, theme: Int) : Dialog(context, theme) {
    //重新设置属性
    private fun setDialog() {
        val dialogWindow = this.window
        dialogWindow?.decorView?.setPadding(0, 0, 0, 0)
        val lp = dialogWindow?.attributes
        lp?.width = WindowManager.LayoutParams.MATCH_PARENT
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialogWindow?.apply {
            this.attributes = lp
            this.setGravity(Gravity.BOTTOM)
            this.setWindowAnimations(R.style.BottomDialogEnterAndExitAnimation)
        }
    }

    //上下文
    class Builder(private val context: Context) {
        private var title: String? = null//标题
        private var contentList: Array<String>? = null//内容
        private var positiveClickListener: DialogInterface.OnClickListener? = null//点击内容按钮监听
        private var cancelColor: Int? = null//取消字体颜色 = 0
        private var contentListColor: IntArray? = null//内容字体颜色


        /**
         * 设置对话框标题
         *
         * @param title 标题字符串
         * @return
         */
        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        /**
         * 设置对话框标题
         *
         * @param title 标题资源id
         * @return
         */
        fun setTitle(title: Int): Builder {
            this.title = context.getText(title) as String
            return this
        }

        /**
         * 设置对话框内容
         *
         * @param contentList 内容字符串数组
         * @return
         */
        fun setContentList(contentList: Array<String>): Builder {
            this.contentList = contentList
            return this
        }

        /**
         * 设置对话框内容
         *
         * @param contentList 内容资源id数组
         * @return
         */
        fun setContentList(contentList: IntArray): Builder {
            for (i in contentList.indices) {
                this.contentList!![i] = context.getText(contentList[i]) as String
            }
            return this
        }

        /**
         * 内容资源字体颜色
         *
         * @param contentListColor
         * @return
         */
        fun setContentListColor(contentListColor: IntArray): Builder {
            this.contentListColor = contentListColor
            return this
        }

        /**
         * 设置取消字体颜色
         *
         * @param cancelColor
         * @return
         */
        fun setCancelColor(cancelColor: Int): Builder {
            this.cancelColor = cancelColor
            return this
        }

        /**
         * 设置"item"点击事件
         *
         * @return
         */
        fun setPositivePositionItem(listener: DialogInterface.OnClickListener): Builder {
            positiveClickListener = listener
            return this
        }

        /**
         * 创建对话框（不显示）
         *
         * @return
         */
        fun create(): BottomDialog {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialog = BottomDialog(context, R.style.CustomDialog) // 使用自定义主题
            val layout: View = inflater.inflate(R.layout.dialog_bottom, null)
            if (title != null) {
                //设置标题
                val botTitle = layout.findViewById<TextView>(R.id.botTitle)
                val botTitleLine = layout.findViewById<View>(R.id.botTitleLine)
                botTitle.apply {
                    this.text = title
                    this.visibility = View.VISIBLE
                }
                botTitleLine.visibility = View.VISIBLE
            }

            //设置内容
            if (contentList != null) {
                val botAddItem = layout.findViewById<LinearLayout>(R.id.botAddItem)
                addItem(dialog, botAddItem)
            }

            //取消
            val botCancel = layout.findViewById<TextView>(R.id.botCancel)
            //设置颜色
            if (cancelColor != 0) {
                cancelColor?.let {
                    botCancel.setTextColor(ContextCompat.getColor(context, it))
                }
            }
            //关闭dialog
            botCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setContentView(layout)
            return dialog
        }

        /**
         * 动态添加item
         *
         * @param dialog
         * @param botAddItem 要添加的布局
         */
        private fun addItem(dialog: BottomDialog, botAddItem: LinearLayout) {
            for (i in contentList!!.indices) {
                //设置内容与横线的容器
                val lay = LinearLayout(context)
                val layLp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lay.layoutParams = layLp
                lay.orientation = LinearLayout.VERTICAL

                //设置内容item，并设置文字
                val textView = TextView(context)
                val lp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    SizeUtils.dp2px(45f)
                )
                textView.apply {
                    this.layoutParams = lp
                    this.gravity = Gravity.CENTER
                    this.setTextColor(Color.parseColor("#02B5F9"))
                    this.textSize = 14f
                    this.text = contentList!![i]
                    this.id = i
                }

                //自定义字体颜色
                if (contentListColor != null) {
                    textView.setTextColor(ContextCompat.getColor(context, contentListColor!![i]))
                }
                lay.addView(textView)

                //设置横线
                if (i <= contentList!!.size - 2) {
                    val botLine = View(context)
                    val lineLp = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        SizeUtils.dp2px(0.5f)
                    )
                    botLine.layoutParams = lineLp
                    botLine.setBackgroundColor(Color.parseColor("#e1e1e1"))
                    lay.addView(botLine)
                }
                botAddItem.addView(lay)

                //设置点击监听事件
                textView.setOnClickListener { view ->
                    positiveClickListener!!.onClick(dialog, view.id)
                    dialog.dismiss()
                }
            }
        }
    }

    init {
        setDialog()
    }
}