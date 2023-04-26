package com.example.newsapi.controller

import com.example.newsapi.model.Article
import com.example.newsapi.model.Source
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/sources")
    fun getSources(@Query("apiKey") apiKey: String): Call<SourcesResponse>

    @GET("/v2/sources")
    fun getSourcesByCategory(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ): Call<SourcesResponse>

    @GET("/v2/everything")
    fun searchArticles(
        @Query("apiKey") apiKey: String,
        @Query("q") query: String,
        @Query("sources") sources: String
    ): Call<ArticlesResponse>


    @GET("/v2/everything")
    fun getArticlesBySource(
        @Query("apiKey") apiKey: String = "",
        @Query("sources") sourceId: String
    ): Call<ArticlesResponse>

}

data class ArticlesResponse(val articles: List<Article>)

data class SourcesResponse(val sources: List<Source>)


