package com.example.food_app.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.getApiService
import kotlinx.coroutines.launch

class RecipeViewModel (application: Application): AndroidViewModel(application){

    private val apiService = application.getApiService()

    private val _recipe = MutableLiveData<ExtendedRecipe>()
    val recipe: LiveData<ExtendedRecipe> get() = _recipe

    fun updateRecipe(id:Int){
        viewModelScope.launch {
        _recipe.postValue(apiService.getAnalyzedRecipe((apiService.getTheRecipes(id))!!))
        }
    }
    fun clearRecipe(){
        _recipe.postValue(ExtendedRecipe())
    }
    fun changeFavState(){
     if(checkInFav()){
         apiService.removeFromFav(_recipe.value!!)
     }else{
         apiService.addToFav(_recipe.value!!)
     }
        _recipe.postValue(_recipe.value)
    }

    fun checkInFav() = apiService.checkInFav(_recipe.value!!)
    fun changeLikeState(){
        val temp = _recipe.value
        temp?.isLiked = !temp?.isLiked!!
        _recipe.value = temp!!

    }
}