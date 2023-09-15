package com.example.food_app.common

interface Adapter<T, K> {
    fun adapt(t:T): K?
}