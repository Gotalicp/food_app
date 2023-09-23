package com.example.food_app.history

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
class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var items = ArrayList<ExtendedRecipe>()

    var itemClickListener: ItemClickListener<ExtendedRecipe>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_view, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun getItemCount() = items.size
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(list: ArrayList<ExtendedRecipe>){
        items = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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