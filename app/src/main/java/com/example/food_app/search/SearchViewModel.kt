package com.example.food_app.search

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.Recipe
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private val apiService = application.getApiService()

    private val _prediction = MutableLiveData<List<String>>()
    val prediction: LiveData<List<String>> get() = _prediction

    private val _results = MutableLiveData<List<ExtendedRecipe>>()
    val results: LiveData<List<ExtendedRecipe>> get() = _results

    private val _recipe = MutableLiveData<ExtendedRecipe>()
    val recipe: LiveData<ExtendedRecipe> get() = _recipe

    private val _predictionState = MutableLiveData<Int>()
    val predictionState: LiveData<Int> get() = _predictionState

    init {
        viewModelScope.launch {
            _results.value = apiService.getRandomRecipes(6)
        }
    }

    fun changeState(state: Int){
        _predictionState.postValue(state)
    }
    fun updatePrediction(query: String) {
        viewModelScope.launch {
            _prediction.value = apiService.getSearchPrediction(query)!!
        }
    }

    fun updateResults(query: String, offset: Int) {
        viewModelScope.launch {
            _results.value = apiService.getRecipesByComplexSearch(query, offset)!!
        }
    }
}