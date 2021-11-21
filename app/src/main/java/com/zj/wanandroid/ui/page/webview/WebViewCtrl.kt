package com.zj.wanandroid.ui.page.webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi

class WebViewCtrl(
    private val mView: FrameLayout,
    private var linkUrl: String,
    private val onWebCall: (isFinish: Boolean) -> Unit
) {

    private val webView by lazy { mView.getChildAt(0) as WebView }
    private val progressBar by lazy { mView.getChildAt(1) as ProgressBar }

    fun initSettings() {
        onWebCall(false)
        setWebSettings()
        setupWebClient()
    }

    fun onDestroy() {
        mView.removeAllViews()
        webView.destroy()
    }

    private fun setWebSettings() {
        val webSettings = webView.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = false
        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件

        //其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "UTF-8"//设置编码格式
    }


    private fun setupWebClient() {
        webView.webViewClient = NewWebViewClient()
        webView.webChromeClient = ProgressWebViewChromeClient()
        refresh()
    }

    fun refresh() {
        webView.loadUrl(linkUrl)
    }


    inner class ProgressWebViewChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }
    }


    inner class NewWebViewClient : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            linkUrl = request?.url.toString()
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            linkUrl = url?:"NullUrlString"
            return super.shouldOverrideUrlLoading(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            progressBar.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            progressBar.visibility = View.GONE
            onWebCall(true)
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            handler?.proceed()
        }
    }

}