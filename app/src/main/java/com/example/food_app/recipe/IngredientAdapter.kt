package com.example.food_app.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.R
import com.example.food_app.data.Ingredient

class IngredientAdapter: RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var items: List<Ingredient> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredients_view, parent, false)
        return IngredientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = items[position]
        holder.bind(ingredient)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(list: List<Ingredient>){
        items = list
        notifyDataSetChanged()
    }
    inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientTextView: TextView = itemView.findViewById(R.id.ingredientTextView)
        fun bind(ingredient: Ingredient) {
            ingredientTextView.text = ingredient.original
        }
    }
}