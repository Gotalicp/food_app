package com.example.food_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application){

    private val apiService = application.getApiService()

    private val _prediction = MutableLiveData<List<String>>()
    val prediction: LiveData<List<String>> get() = _prediction
    fun updatePrediction(query:String){
        viewModelScope.launch {
            _prediction.value = apiService.getSearchPrediction(query)!!
        }
    }
}