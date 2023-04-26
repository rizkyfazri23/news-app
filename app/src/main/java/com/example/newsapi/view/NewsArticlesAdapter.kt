package com.example.newsapi.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.R
import com.example.newsapi.model.Article

class NewsArticlesAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsArticlesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        val authorTextView: TextView = itemView.findViewById(R.id.author_text_view)
        val descriptionTextView: TextView? = itemView.findViewById(R.id.description_text_view)
        val articleImageView: ImageView = itemView.findViewById(R.id.article_image_view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_article_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.authorTextView.text = article.author
        holder.descriptionTextView?.text = article.description

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(holder.articleImageView)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsArticleDetailsActivity::class.java)
            intent.putExtra("url", article.url)
            holder.itemView.context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int = articles.size
}

