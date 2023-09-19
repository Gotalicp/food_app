package com.example.recipe_app

import android.app.Application
import android.util.Log
import com.example.food_app.api.RetrofitSpoonacular

class MyApplication: Application() {

    val apiService: RetrofitSpoonacular by lazy {
        RetrofitSpoonacular.getApi()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("hello", "MESSAGE")
    }
}

fun Application.getApiService() = (this as MyApplication).apiService