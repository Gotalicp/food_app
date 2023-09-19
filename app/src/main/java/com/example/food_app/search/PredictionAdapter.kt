package com.example.food_app.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.R

class PredictionAdapter: RecyclerView.Adapter<PredictionAdapter.PredictionViewHolder>() {

    private var items: List<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_prediction_view, parent, false)
        return PredictionViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    fun updateItems(list: List<String>){
        Log.d("items",items.toString())
        items = list
        Log.d("items",items.toString())
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(items[position])
    }
    inner class PredictionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.findViewById<TextView>(R.id.predictionTextView)

        fun bind(text:String) {
            textView.text = text
        }
    }
}