package com.example.food_app.api

import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.Recipe


interface RecipeApiService {

    suspend fun getRecipesByComplexSearch(query: String): List<Recipe>
    suspend fun getRandomRecipe(): Recipe?
    suspend fun getBulkRecipes(): List<ExtendedRecipe>
    suspend fun getSearchPrediciton(): List<String>?
}