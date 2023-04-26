package com.example.newsapi.view

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapi.R

class NewsArticleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_article_details)

        // Get the URL of the article from the intent
        val articleUrl = intent.getStringExtra("url")

        // Find the WebView in the layout
        val webView = findViewById<WebView>(R.id.web_view)

        // Enable JavaScript in the WebView
        webView.settings.javaScriptEnabled = true

        // Load the URL into the WebView
        if (articleUrl != null) {
            webView.loadUrl(articleUrl)
        }
    }
}
