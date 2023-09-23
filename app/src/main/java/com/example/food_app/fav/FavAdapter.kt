package com.example.food_app.fav

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.R
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.ItemClickListener

class FavAdapter: RecyclerView.Adapter<FavAdapter.FavViewHolder>() {

    private var items = mutableListOf<ExtendedRecipe>()

    var itemClickListener: ItemClickListener<ExtendedRecipe>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_view, parent, false)
        return FavViewHolder(itemView)
    }

    override fun getItemCount() = items.size
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(list: List<ExtendedRecipe>){
        items = list.toMutableList()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class FavViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageView = view.findViewById<ImageView>(R.id.resultImageView)
        private val textView = view.findViewById<TextView>(R.id.resultTextView)

        fun bind(recipe: ExtendedRecipe) {
            Glide.with(imageView)
                .load(recipe.image)
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