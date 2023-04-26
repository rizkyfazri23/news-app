package com.example.newsapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.adapter.NewsSourcesAdapter
import com.example.newsapi.controller.SourcesResponse
import com.example.newsapi.model.NewsServiceFactory
import com.example.newsapi.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsSourcesActivity : AppCompatActivity() {

    private lateinit var newsSourcesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_sources)

        newsSourcesRecyclerView = findViewById(R.id.news_sources_recycler_view)
        newsSourcesRecyclerView.layoutManager = LinearLayoutManager(this)

        val categoryName = intent.getStringExtra("categoryName") ?: ""
        title = categoryName

        val newsService = NewsServiceFactory.create()

        newsService.getSourcesByCategory(Constants.API_KEY, categoryName).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if (response.isSuccessful) {
                    val sources = response.body()?.sources ?: emptyList()
                    newsSourcesRecyclerView.adapter = NewsSourcesAdapter(sources)
                } else {
                    Toast.makeText(this@NewsSourcesActivity, "Failed to get sources", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Toast.makeText(this@NewsSourcesActivity, "Failed to get sources", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
