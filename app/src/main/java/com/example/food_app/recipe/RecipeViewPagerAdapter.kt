package com.example.food_app.recipe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.data.AnalyzedRecipe
import com.example.food_app.data.Step
import com.example.food_app.databinding.InstructionsViewBinding

class RecipeViewPagerAdapter: RecyclerView.Adapter<RecipeViewPagerAdapter.Pager2ViewHolder>() {
    
    private var items:List<Step> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val binding = InstructionsViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Pager2ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val instruction = items[position]
        holder.bind(instruction)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    fun updateItems(newItems: List<Step>){
        items = newItems
        notifyDataSetChanged()
    }
    inner class Pager2ViewHolder(private val binding: InstructionsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(instruction: Step) {
            binding.instruction.text = "Step ${instruction.step}"
            binding.stepCounter.text = instruction.number.toString()
        }
    }
}