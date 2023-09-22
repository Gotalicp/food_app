package com.example.food_app

import android.app.Application
import com.example.food_app.api.RetrofitSpoonacular

class MyApplication: Application() {

    val apiService: RetrofitSpoonacular by lazy {
        RetrofitSpoonacular.getApi()
    }
    }
fun Application.getApiService() = (this as MyApplication).apiService