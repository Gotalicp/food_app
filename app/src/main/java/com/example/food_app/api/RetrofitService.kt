package com.example.food_app.api

import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.FoodItem
import com.example.food_app.data.RandomFoodTriviaResponse

interface RetrofitService {

    suspend fun getRecipesByComplexSearch(query: String,offset:Int): List<ExtendedRecipe>
    suspend fun getRandomRecipes(number:Int): List<ExtendedRecipe>?
    suspend fun getBulkRecipes(ids: List<Int>): List<ExtendedRecipe>
    suspend fun getTheRecipes(id: Int): ExtendedRecipe?
    suspend fun getSearchPrediction(query:String): List<String>?
    suspend fun getAnalyzedRecipe(recipe:ExtendedRecipe): ExtendedRecipe
    suspend fun getTexts(text:String): MutableList<FoodItem>
    suspend fun getRandomTrivia(): RandomFoodTriviaResponse
    suspend fun getRandomJoke(): RandomFoodTriviaResponse
}