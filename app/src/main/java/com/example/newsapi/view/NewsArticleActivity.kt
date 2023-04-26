package com.example.newsapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.controller.ArticlesResponse
import com.example.newsapi.R
import com.example.newsapi.adapter.NewsArticlesAdapter
import com.example.newsapi.model.NewsServiceFactory
import com.example.newsapi.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsArticleActivity : AppCompatActivity() {

    private lateinit var newsArticlesRecyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var titleTextView: TextView

    private var sourceId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_article)

        newsArticlesRecyclerView = findViewById(R.id.news_articles_recycler_view)
        newsArticlesRecyclerView.layoutManager = LinearLayoutManager(this)

        searchView = findViewById(R.id.article_search_edit_text)
        titleTextView = findViewById(R.id.title_text_view)

        sourceId = intent.getStringExtra("sourceId") ?: ""
        titleTextView.text = sourceId


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchArticles(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val newsService = NewsServiceFactory.create()

        newsService.getArticlesBySource(Constants.API_KEY, sourceId).enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    newsArticlesRecyclerView.adapter = NewsArticlesAdapter(articles)
                } else {
                    Toast.makeText(this@NewsArticleActivity, "Failed to load articles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Toast.makeText(this@NewsArticleActivity, "Error: " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun searchArticles(query: String) {
        val newsService = NewsServiceFactory.create()

        newsService.searchArticles(Constants.API_KEY, query, sourceId).enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    newsArticlesRecyclerView.adapter = NewsArticlesAdapter(articles)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody ?: "Unknown error"
                    Toast.makeText(this@NewsArticleActivity, "Search articles request failed with message: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                val errorMessage = t.message ?: "Unknown error"
                Toast.makeText(this@NewsArticleActivity, "Search articles request failed with message: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
