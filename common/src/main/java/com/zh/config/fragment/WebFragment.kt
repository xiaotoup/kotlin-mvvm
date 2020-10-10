package com.zh.config.fragment

import android.net.http.SslError
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.ViewDataBinding
import com.just.agentweb.AgentWeb
import com.zh.common.BR
import com.zh.common.R
import com.zh.common.base.BaseFragment
import com.zh.common.base.viewmodel.NormalViewModel
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * 解决NestedScrollView嵌套WebView问题
 */
class WebFragment : BaseFragment<ViewDataBinding, NormalViewModel>() {

    private var mAgentWeb: AgentWeb? = null
    private var mScrollView: NestedScrollView? = null

    override val layoutRes = R.layout.fragment_web

    override fun viewModel() = NormalViewModel()

    override val onBindVariableId = BR.viewModel

    override fun initView(savedInstanceState: Bundle?) {
        mAgentWeb = AgentWeb.with(this) //传入Activity
            .setAgentWebParent(
                container, LinearLayout.LayoutParams(-1, -1)
            ) //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
            .useDefaultIndicator() // 使用默认进度条
            .createAgentWeb() //
            .ready()
            .go(null)
    }

    override fun initData() {
        settings
    }

    fun loadUrl(url: String) {
        mAgentWeb!!.urlLoader.loadUrl(url)
    }

    fun loadUrl(url: String, scrollView: NestedScrollView) {
        mScrollView = scrollView
        mAgentWeb!!.urlLoader.loadUrl(url)
    }

    /**
     * 加载富文本
     */
    fun loadDataWithBaseURL(data: String) {
        loadData(data)
    }

    fun loadDataWithBaseURL(data: String, scrollView: NestedScrollView) {
        mScrollView = scrollView
        loadData(data)
    }

    private fun loadData(data: String) {
        val linkCss = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:0px;} " +
                "img{max-width:100%;height:auto;}" +
                "</style></head>"
        val html = "<html>$linkCss<body>$data</body></html>"
        mAgentWeb!!.urlLoader.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    private fun onMeasure() {
        if (mScrollView == null) return
        //webView加载完成之后重新测量webView的高度
        val params = mAgentWeb!!.webCreator.webView.layoutParams
        params.width = resources.displayMetrics.widthPixels
        params.height = mAgentWeb!!.webCreator.webView.height - mScrollView!!.height
        mAgentWeb!!.webCreator.webView.layoutParams = params
    }

    //设置webView属性
    private val settings: Unit
        private get() {
            val settings = mAgentWeb!!.webCreator.webView.settings
            //扩大比例的缩放
            settings.useWideViewPort = true
            // 设置可以支持缩放
            settings.setSupportZoom(false)
            settings.builtInZoomControls = true
            //自适应屏幕
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            settings.loadWithOverviewMode = true
            //支持javascript
            settings.javaScriptEnabled = true
            //支持通过JS打开新窗口
            settings.javaScriptCanOpenWindowsAutomatically = true
            //关闭缓存
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.setAppCacheEnabled(false)
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            mAgentWeb!!.webCreator.webView.webViewClient = object : WebViewClient() {
                override fun onReceivedSslError(
                    view: WebView,
                    handler: SslErrorHandler,
                    error: SslError
                ) {
                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                    }*/
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    onMeasure()
                }
            }
        }


    override fun onPause() {
        mAgentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mAgentWeb != null) {
            mAgentWeb!!.webLifeCycle.onDestroy()
        }
    }
}