package com.theappcoderz.admobcustomeads

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class StandardWebView : AppCompatActivity() {

    // Declare Variables
    private var webview: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Prepare the progress bar
        setContentView(R.layout.webview_browser)
        // Locate the WebView in webview_browser.xmlwser.xml
        webview = findViewById(R.id.webview)

        // Enable Javascript to run in WebView
        webview!!.settings.javaScriptEnabled = true

        // Allow Zoom in/out controls
        webview!!.settings.builtInZoomControls = false

        // Zoom out the best fit your screen
        webview!!.settings.loadWithOverviewMode = true
        webview!!.settings.useWideViewPort = true

        // Load URL
        webview!!.loadUrl(intent.getStringExtra("ppurl").toString())

        // Show the progress bar
        webview!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
            }
        }

        // Call private class InsideWebViewClient
        webview!!.webViewClient = InsideWebViewClient()

    }

    private inner class InsideWebViewClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override// Force links to be opened inside WebView and not in Default Browser
        fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false

        }

    }

}