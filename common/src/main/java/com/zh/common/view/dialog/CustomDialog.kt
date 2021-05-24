package com.zh.common.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableString
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zh.common.R

/**
 * 自定义警告对话框
 */
class CustomDialog : Dialog {
    constructor(context: Context) : this(context, 0)
    constructor(context: Context, theme: Int) : super(context, theme)
    constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener
    ) : super(context, cancelable, cancelListener)

    class Builder(//上下文
        private val context: Context
    ) {
        private var title: String? = null//标题
        private var message: String? = null//内容
        private var positiveBtnText: String? = null//确定按钮文字
        private var positiveBtnTextColor: Int? = null//确定按钮文字颜色 = 0
        private var negativeBtnText: String? = null//取消按钮文字
        private var negativeBtnTextColor: Int? = null//取消按钮文字颜色 = 0
        private var contentView: View? = null
        private var positiveButtonClickListener: DialogInterface.OnClickListener? = null//确定按钮监听
        private var negativeButtonClickListener: DialogInterface.OnClickListener? = null//取消按钮监听
        private var contentSpannableString: SpannableString? = null//文字样式
        private var canCancel = true //是否可以取消对话框
        private var widthPercent: Float? = null//对话框宽度占屏幕宽度的百分比

        /**
         * 设置对话框的内容
         *
         * @param message 内容字符串
         * @return
         */
        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        /**
         * 设置对话框的内容
         *
         * @param message 内容资源id
         * @return
         */
        fun setMessage(message: Int): Builder {
            this.message = context.getText(message) as String
            return this
        }

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
         * 设置自定义对话框使用的布局
         *
         * @param view
         * @return
         */
        fun setContentView(view: View): Builder {
            contentView = view
            return this
        }

        fun setPositiveBtnTextColor(color: Int): Builder {
            positiveBtnTextColor = color
            return this
        }

        fun setNegativeBtnTextColor(color: Int): Builder {
            negativeBtnTextColor = color
            return this
        }

        /**
         * 设置"确定"点击事件和文字
         *
         * @param positiveButtonText 文字资源id
         * @return
         */

        fun setPositiveButton(
            positiveButtonText: String,
            listener: DialogInterface.OnClickListener
        ): Builder {
            positiveBtnText = positiveButtonText
            positiveButtonClickListener = listener
            return this
        }

        fun setPositiveButton(positiveButtonText: String): Builder {
            positiveBtnText = positiveButtonText
            return this
        }

        fun setPositiveButton(
            positiveButtonText: Int,
            listener: DialogInterface.OnClickListener
        ): Builder {
            positiveBtnText = context.getText(positiveButtonText) as String
            positiveButtonClickListener = listener
            return this
        }

        fun setPositiveButton(positiveButtonText: Int): Builder {
            positiveBtnText = context.getText(positiveButtonText) as String
            return this
        }

        /**
         * 设置"取消"点击事件和文字
         *
         * @param negativeButtonText 文字字符串
         * @param listener
         * @return
         */
        fun setNegativeButton(
            negativeButtonText: String,
            listener: DialogInterface.OnClickListener
        ): Builder {
            negativeBtnText = negativeButtonText
            negativeButtonClickListener = listener
            return this
        }

        fun setNegativeButton(negativeButtonText: String): Builder {
            negativeBtnText = negativeButtonText
            return this
        }

        fun setNegativeButton(
            negativeButtonText: Int,
            listener: DialogInterface.OnClickListener
        ): Builder {
            negativeBtnText = context.getText(negativeButtonText) as String
            negativeButtonClickListener = listener
            return this
        }

        fun setNegativeButton(negativeButtonText: Int): Builder {
            negativeBtnText = context.getText(negativeButtonText) as String
            return this
        }

        /**
         * 设置Content的ForegroundColorSpan
         *
         * @param spannableString
         * @return
         */
        fun setContentForegroundColorSpan(spannableString: SpannableString?): Builder {
            contentSpannableString = spannableString
            return this
        }

        /**
         * 是否可以取消对话框
         *
         * @param flag
         * @return
         */
        fun setCancelable(flag: Boolean): Builder {
            canCancel = flag
            return this
        }

        /**
         * 设置对话框的宽度占屏幕宽度的比例
         *
         * @param widthPercent
         * @return
         */
        fun setWidthPercent(widthPercent: Float): Builder {
            this.widthPercent = widthPercent
            return this
        }

        /**
         * 创建对话框（不显示）
         *
         * @return
         */
        fun create(): CustomDialog {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialog = CustomDialog(context, R.style.CustomDialog) // 使用自定义主题
            val layout: View = inflater.inflate(R.layout.custom_dialog_layout, null)
            dialog.addContentView(
                layout, LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )

            //对话框宽度占屏幕宽度的比例
            if (widthPercent != null) {
                val widthPixels = context.resources.displayMetrics.widthPixels
                val dialogWindow = dialog.window
                val lp = dialogWindow?.attributes
                lp?.width = (widthPixels * widthPercent!!).toInt()
                dialogWindow?.attributes = lp
            }
            dialog.setCancelable(canCancel) //是否可以取消对话框

            //设置标题
            val tvTitle = layout.findViewById<TextView>(R.id.tvTitle)
            if (!TextUtils.isEmpty(title)) {
                tvTitle.text = title
                tvTitle.visibility = View.VISIBLE
            }

            // 设置内容
            var tvMessage: TextView? = null
            if (message != null) {
                tvMessage = layout.findViewById<View>(R.id.tvMessage) as TextView
                if (contentSpannableString != null) {
                    tvMessage.text = contentSpannableString
                } else {
                    tvMessage.text = message
                }
            } else if (contentView != null) {
                (layout.findViewById<View>(R.id.llContent) as LinearLayout).removeAllViews()
                (layout.findViewById<View>(R.id.llContent) as LinearLayout).addView(
                    contentView,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }
            if (message.isNullOrEmpty()) {
                tvMessage = layout.findViewById<View>(R.id.tvMessage) as TextView
                tvMessage.visibility = View.GONE
            }

            // 设置确认按钮
            if (positiveBtnText != null) {
                val textView = layout.findViewById<View>(R.id.tvConfrim) as TextView
                textView.text = positiveBtnText
                if (positiveButtonClickListener != null) {
                    textView.setOnClickListener {
                        positiveButtonClickListener?.onClick(
                            dialog,
                            DialogInterface.BUTTON_POSITIVE
                        )
                    }
                } else {
                    textView.setOnClickListener {
                        dialog.dismiss()
                    }
                }

                //确定按钮文字颜色
                if (positiveBtnTextColor != 0) {
                    positiveBtnTextColor?.let {
                        textView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                it
                            )
                        )
                    }
                }
            } else {
                // 没有设置"确认"按钮文字，隐藏
                layout.findViewById<View>(R.id.tvConfrim).visibility = View.GONE
            }

            // 设置"取消"按钮
            if (negativeBtnText != null) {
                val textView = layout.findViewById<View>(R.id.tvCancel) as TextView
                textView.text = negativeBtnText
                if (negativeButtonClickListener != null) {
                    textView.setOnClickListener {
                        negativeButtonClickListener?.onClick(
                            dialog,
                            DialogInterface.BUTTON_NEGATIVE
                        )
                    }
                } else {
                    textView.setOnClickListener {
                        dialog.dismiss()
                    }
                }

                //取消按钮文字颜色
                if (negativeBtnTextColor != 0) {
                    negativeBtnTextColor?.let {
                        textView.setTextColor(
                            ContextCompat.getColor(
                                context,
                                it
                            )
                        )
                    }
                }
            } else {
                // 没有设置"取消"按钮文字，隐藏
                layout.findViewById<View>(R.id.tvCancel).visibility = View.GONE
            }
            dialog.setContentView(layout)
            return dialog
        }
    }
}