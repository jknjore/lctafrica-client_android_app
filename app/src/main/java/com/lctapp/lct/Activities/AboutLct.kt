package com.lctapp.lct.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lctapp.lct.R

class AboutLct : AppCompatActivity() {

    lateinit var mWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_lct)

        mWebView = findViewById(R.id.webView)

        mWebView = WebView(this)
        setContentView(mWebView)
        mWebView.webViewClient= WebViewClient()
        mWebView.loadUrl("http://lctafrica.net/")
        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        mWebView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack()
        }else{
            super.onBackPressed()
        }

    }
}