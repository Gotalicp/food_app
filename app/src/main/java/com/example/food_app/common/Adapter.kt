package com.example.food_app.common

import com.example.food_app.data.ExtendedRecipe

interface Adapter<T, K> {
    fun adapt(t: T): K?
}