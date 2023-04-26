package com.example.newsapi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.adapter.NewsCategoryAdapter
import com.example.newsapi.controller.SourcesResponse
import com.example.newsapi.model.Category
import com.example.newsapi.model.NewsServiceFactory
import com.example.newsapi.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categoryRecyclerView = findViewById(R.id.category_recyclerview)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)

        val newsService = NewsServiceFactory.create()

        newsService.getSources(Constants.API_KEY).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if (response.isSuccessful) {
                    val sources = response.body()?.sources ?: emptyList()
                    val categories = sources.map { Category(it.id, it.category) }
                    val filteredCategories = removeDuplicateCategories(categories)
                    categoryRecyclerView.adapter = NewsCategoryAdapter(filteredCategories) { category ->
                        showNewsSources(category.name)
                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(this@MainActivity, "Error: ${response.code()} ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun removeDuplicateCategories(categories: List<Category>): List<Category> {
        val distinctCategories = categories.distinctBy { it.name }
        val filteredCategories = mutableListOf<Category>()

        for (category in distinctCategories) {
            if (!filteredCategories.any { it.id == category.id }) {
                filteredCategories.add(category)
            }
        }

        return filteredCategories
    }

    private fun showNewsSources(categoryName: String) {
        val intent = Intent(this, NewsSourcesActivity::class.java)
        intent.putExtra("categoryName", categoryName)
        startActivity(intent)
    }
}
