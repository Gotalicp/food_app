package com.example.food_app.Search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.R
import com.example.food_app.data.Recipe

class ResultAdapter(text:String): RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private val items = mutableListOf<Recipe>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_view, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageView = view.findViewById<ImageView>(R.id.resultImageView)
        private val textView = view.findViewById<TextView>(R.id.resultTextView)


        fun bind(recipe: Recipe) {
            Glide.with(textView)
                .load(recipe.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .centerCrop()
                .into(holder)
        textView.apply { setRoundedCorners(20F) }
        }
    }
}