package com.example.food_app.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.Recipe
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application){

    private val apiService = application.getApiService()

    private val _prediction = MutableLiveData<List<String>>()
    val prediction: LiveData<List<String>> get() = _prediction

    private val _results = MutableLiveData<List<Recipe>>()
    val results: LiveData<List<Recipe>> get() = _results

    init {
        viewModelScope.launch {
            _results.value = apiService.getRandomRecipes(6)

        }
    }

    fun updatePrediction(query:String){
        viewModelScope.launch {
            _prediction.value = apiService.getSearchPrediction(query)!!
        }
    }

    fun updateResults(query:String, offset:Int){
        viewModelScope.launch {
            _results.value = apiService.getRecipesByComplexSearch(query,offset)!!
        }
    }

//    init {
//        _results.value = apiService.
//    }
}