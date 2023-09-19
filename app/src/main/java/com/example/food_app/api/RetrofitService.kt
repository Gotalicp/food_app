package com.example.food_app.api

import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.Recipe


interface RetrofitService {

    suspend fun getRecipesByComplexSearch(query: String,offset:Int): List<Recipe>
    suspend fun getRandomRecipe(): Recipe?
    suspend fun getBulkRecipes(ids: String): List<ExtendedRecipe>
    suspend fun getTheRecipes(id: Int): ExtendedRecipe?
    suspend fun getSearchPrediction(query:String): List<String>?
}