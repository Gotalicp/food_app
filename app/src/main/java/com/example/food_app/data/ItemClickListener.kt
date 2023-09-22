package com.example.food_app.data
interface ItemClickListener<T> {
    fun onItemClicked(item: T, itemPosition: Int)
}