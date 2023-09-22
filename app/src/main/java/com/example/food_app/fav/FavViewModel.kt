package com.example.food_app.fav

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.getApiService
import kotlinx.coroutines.launch

class FavViewModel(application: Application): AndroidViewModel(application) {

    private val apiService = application.getApiService()

    private val _results = MutableLiveData<List<ExtendedRecipe>>()
    val results: LiveData<List<ExtendedRecipe>> get() = _results

    init {
        viewModelScope.launch {
            _results.value = apiService.getFavourite()
        }
    }
    fun getFav(){
        viewModelScope.launch {
            _results.value = apiService.getFavourite()
        }
    }
}