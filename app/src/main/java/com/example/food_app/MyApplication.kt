package com.example.food_app

import android.app.Application
import com.example.food_app.api.HtmlRetrofitService
import com.example.food_app.api.RetrofitSpoonacular

class MyApplication: Application() {

    val apiService: RetrofitSpoonacular by lazy {
        RetrofitSpoonacular.getApi()
    }
    val htmlService: HtmlRetrofitService by lazy {
        HtmlRetrofitService.getHtml()
    }
    }
fun Application.getApiService() = (this as MyApplication).apiService
fun Application.getHtmlService() = (this as MyApplication).htmlService