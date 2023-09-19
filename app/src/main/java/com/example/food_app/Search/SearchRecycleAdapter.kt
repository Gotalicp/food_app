package com.example.food_app.Search

import android.view.LayoutInflater
import android.view.View;
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.R
import com.example.food_app.data.Recipe

class SearchRecycleAdapter : RecyclerView.Adapter<SearchRecycleAdapter.SearchViewHolder>() {

    private val items = ArrayList<Recipe>()

    var itemClickListener: ItemClickListener<Recipe>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_design, parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<Recipe>) {
            val oldList: List<Recipe> = ArrayList(items)

            items.clear()
            items.addAll(newItems)

        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newItems[newItemPosition]
        }
        }).dispatchUpdatesTo(this)
    }
    inner class SearchViewHolder(view:View) : RecyclerView.ViewHolder(view) {

        fun bind(searchResult: Recipe) {
            //TODO()
        }
    }
}



interface ItemClickListener<T> {
    fun onItemClicked(item: T, itemPosition: Int)
}