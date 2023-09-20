package com.example.food_app.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.ExtendedRecipe
import com.example.recipe_app.getApiService
import kotlinx.coroutines.launch

class RecipeViewModel (application: Application): AndroidViewModel(application){

    private val apiService = application.getApiService()

    private val _recipe = MutableLiveData<ExtendedRecipe>()
    val recipe: LiveData<ExtendedRecipe> get() = _recipe

    fun updateRecipe(id:Int){
        viewModelScope.launch {
            _recipe.value = apiService.getTheRecipes(id)
            _recipe.value?.let { apiService.getAnalyzedRecipe(it) }
        }
    }
}