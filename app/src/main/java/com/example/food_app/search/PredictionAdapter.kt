package com.example.food_app.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.R

class PredictionAdapter: RecyclerView.Adapter<PredictionAdapter.PredictionViewHolder>() {

    private var items: List<String> = mutableListOf()

    var itemClickListener: ItemClickListener<String>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_prediction_view, parent, false)
        return PredictionViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    fun updateItems(list: List<String>){
        items = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class PredictionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.predictionTextView)

        fun bind(text:String) {
            textView.text = text
            textView.setOnClickListener {
                itemClickListener?.onItemClicked(text, absoluteAdapterPosition)
            }
        }
    }
    interface ItemClickListener<T> {
        fun onItemClicked(item: T, itemPosition: Int)
    }
}