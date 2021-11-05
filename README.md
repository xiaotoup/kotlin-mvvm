### kotlin-mvvm
kotlin mvvm+dataBinding+retrofit2+ARouter等BaseActivity、BaseFragment、BaseDialogFragment基类封装

Android开发项目基本使用框架，封装了各类组件，在基类实现了沉浸式状态栏，可以自己更改颜色，更高效全能开发框架

![Screenshot_2021-11-05-15-46-03-512_com zh kotlin_mvvm](https://user-images.githubusercontent.com/32659960/140476015-c2c98786-2e17-4871-af63-b67450d34b11.jpg")
<img src="https://user-images.githubusercontent.com/32659960/140476015-c2c98786-2e17-4871-af63-b67450d34b11.jpg" width="400" height="800"/>


里面封装各种组件：
## RelativeItemView 一个item，左右文字图片一个控件完美使用
```
<com.zh.common.view.RelativeItemView
                    android:layout_width="match_parent"
                    android:layout_height="40dp"
                    app:riv_driverColor="@color/colorPrimaryDark"
                    app:riv_driverMarginHorizontal="20dp"
                    app:riv_driverShow="true"
                    app:riv_editTextGravity="left"
                    app:riv_editTextHint="请输入"
                    app:riv_editTextMarginLeft="50dp"
                    app:riv_editTextPaddingLeft="15dp"
                    app:riv_editTextSize="12sp"
                    app:riv_leftText="微信"
                    app:riv_leftTextColor="@color/colorPrimary"
                    app:riv_leftTextDrawable="@drawable/picture_icon_back_arrow"
                    app:riv_leftTextDrawablePadding="5dp"
                    app:riv_leftTextPaddingLeft="15dp"
                    app:riv_leftTextSize="16sp"
                    app:riv_leftTextStyle="bold"
                    app:riv_rightText="235"
                    app:riv_rightTextColor="@color/color_text_999"
                    app:riv_rightTextDrawable="@drawable/picture_icon_arrow_down"
                    app:riv_rightTextDrawablePadding="5dp"
                    app:riv_rightTextDrawableTint="@color/color_text_999"
                    app:riv_rightTextPaddingRight="15dp"
                    app:riv_rightTextSize="13sp" />
```
# 属性
```
riv_leftImg 左边图片
riv_leftImgWidth 左边图片宽度 
riv_leftImgHeight 左边图片高度 
riv_leftText 左边文字 
riv_leftTextColor 左边文字颜色
riv_leftTextSize 左边文字_字体大小 
riv_leftTextPaddingLeft 左边文字_据左边距离
riv_leftTextDrawable 左边文字_drawableLeft图片 
riv_leftTextDrawableRight 左边文字_drawableLeft图片是否在右边 
riv_leftTextDrawablePadding 左边文字_drawablePadding
riv_leftTextDrawableTint 左边文字_drawableTint
riv_leftTextStyle 左边文字_加粗属性 
riv_editText Edit文字 
riv_editTextColor Edit文字颜色 
riv_editTextHint Edit的hint文字 
riv_editTextHintColor Edit的hint文字颜色
riv_editTextSize Edit文字_字体大小 
riv_editTextEnabled Edit是否可以编辑 
riv_editTextBackground Edit背景 
riv_editTextGravity Edit的gravity位置 
riv_editTextMarginLeft Edit文字_MarginLeft
riv_editTextMarginRight Edit文字_MarginRight 
riv_editTextPaddingLeft Edit文字_PaddingLeft 
riv_editTextPaddingRight Edit文字_PaddingRight 
riv_editTextStyle Edit文字_加粗属性
riv_rightText 右边文字
riv_rightTextHint 右边文字hint
riv_rightTextHintColor 右边文字hint颜色
riv_rightTextColor 右边文字颜色
riv_rightTextSize 右边文字_字体大小
riv_rightTextPaddingRight 右边文字_据左边距离 
riv_rightTextDrawable 右边文字_drawableRight图片 
riv_rightTextDrawablePadding 右边文字_drawablePadding
riv_rightTextDrawableTint 右边文字_drawableTint 
riv_rightTextStyle 右边文字_加粗属性 
riv_driverShow 下划线是否显示
riv_driverColor 下划线颜色 
riv_driverHeight 下划线高度
riv_driverMarginLeft 下划线距离左边距离 
riv_driverMarginRight 下划线距离右边距离
riv_driverMarginHorizontal 下划线距离左右边距离 
```
## TitleBarView 通用标题栏封装
```
<com.zh.common.view.TitleBarView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tb_centerText="测试"
            app:tb_leftImageDrawable="@drawable/picture_icon_back_arrow" />
```
