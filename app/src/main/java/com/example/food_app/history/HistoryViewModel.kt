package com.example.food_app.history

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.food_app.data.ExtendedRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryViewModel(application: Application): AndroidViewModel(application) {

    private val sharedPreferences =
        application.getSharedPreferences("history", Context.MODE_PRIVATE)

    private val _cache = MutableLiveData<ArrayList<ExtendedRecipe>?>()
    val cache: LiveData<ArrayList<ExtendedRecipe>?> get() = _cache

    init {
        _cache.postValue(getRecipeList())
    }
    fun addToHistory(recipe: ExtendedRecipe) {
        val currentCache = _cache.value ?: ArrayList()
        if (recipe.extendedIngredients?.isNotEmpty() == true) {
            currentCache.add(recipe)
            _cache.postValue(currentCache)
            _cache.value?.let { saveRecipeList(it) }
        }
    }
    private fun saveRecipeList(recipeList: ArrayList<ExtendedRecipe>) {
        val json = Gson().toJson(recipeList)
        sharedPreferences.edit().putString("history", json).apply()
    }
    private fun getRecipeList(): ArrayList<ExtendedRecipe>? {
        val json = sharedPreferences.getString("history", null)
        return if (json != null) {
            val type = object : TypeToken<ArrayList<ExtendedRecipe>>() {}.type
            Gson().fromJson(json, type)
        } else {
            null
        }
    }
    fun clearRecipeList() {
        sharedPreferences.edit().remove("history").apply()
    }
}