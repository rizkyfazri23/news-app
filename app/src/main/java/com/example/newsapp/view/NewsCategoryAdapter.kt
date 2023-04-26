package com.example.newsapi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.databinding.CategoryItemBinding
import com.example.newsapi.model.Category

class NewsCategoryAdapter(val categories: List<Category>, val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<NewsCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = categories[position]
        holder.binding.categoryName.text = category.name

        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }

        // kondisi untuk mengecek tema category
        when (category.name) {
            "sports" -> {
                holder.binding.categoryImage.setImageResource(R.drawable.sports)
                holder.binding.categoryDescription.text = "Latest news and updates on various sports and sporting events around the world."
            }
            "business" -> {
                holder.binding.categoryImage.setImageResource(R.drawable.business)
                holder.binding.categoryDescription.text = "News and updates on global business, finance, and the stock market."
            }
            "technology" -> {
                holder.binding.categoryImage.setImageResource(R.drawable.technology)
                holder.binding.categoryDescription.text = "Latest news and developments in the world of technology, including gadgets, software, and innovation."
            }
            "entertainment" -> {
                holder.binding.categoryImage.setImageResource(R.drawable.entertainment)
                holder.binding.categoryDescription.text = "News and updates on movies, TV shows, celebrities, and the entertainment industry."
            }
            "health" -> {
                holder.binding.categoryImage.setImageResource(R.drawable.health)
                holder.binding.categoryDescription.text = "News and updates on health, fitness, and wellness."
            }
            "science" -> {
                holder.binding.categoryImage.setImageResource(R.drawable.science)
                holder.binding.categoryDescription.text = "Latest news and developments in science, including space, technology, environment, and more."
            }
            else -> {
                holder.binding.categoryImage.setImageResource(R.drawable.general)
                holder.binding.categoryDescription.text = "General news and updates from around the world."
            }
        }

    }

    override fun getItemCount() = categories.size

    class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)
}
