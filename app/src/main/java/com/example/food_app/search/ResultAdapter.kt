package com.example.food_app.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.R
import com.example.food_app.data.Recipe

class ResultAdapter: RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private var items = mutableListOf<Recipe>()

    var itemClickListener: ItemClickListener<Recipe>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_view, parent, false)
        return ResultViewHolder(itemView)
    }

    override fun getItemCount() = items.size
    fun updateItems(list: List<Recipe>){
        items = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageView = view.findViewById<ImageView>(R.id.resultImageView)
        private val textView = view.findViewById<TextView>(R.id.resultTextView)

        fun bind(recipe: Recipe) {
            Glide.with(imageView)
                .load(recipe.imageUrl)
                .placeholder(R.drawable.loading)
                .centerCrop()
                .into(imageView)
            textView.text = recipe.title
            itemView.setOnClickListener {
                itemClickListener?.onItemClicked(recipe, absoluteAdapterPosition)
            }
        }
    }
}
interface ItemClickListener<T> {
    fun onItemClicked(item: T, itemPosition: Int)
}