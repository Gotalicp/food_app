package com.example.food_app.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.food_app.getApiService

class SettingViewModel(application: Application): AndroidViewModel(application){
    private val apiService = application.getApiService()
    fun logout(){
        apiService.killFav()
    }
}