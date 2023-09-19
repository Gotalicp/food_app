package com.example.food_app.api

import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.RawResult
import com.example.food_app.data.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class RetrofitSpoonacular {
    companion object {
        const val API_KEY = ""
        const val API_HOST = "https://api.spoonacular.com/"

        private var api: RetrofitSpoonacular? = null
        fun getApi() = api  ?: RetrofitSpoonacular()
    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        recipeApi = retrofit.create()
    }

//    override suspend fun getRecipesByComplexSearch(query: String): List<Recipe>
//    override suspend fun getRandomRecipe(): Recipe?
//    override suspend fun getBulkRecipes(): List<ExtendedRecipe>?
//    override suspend fun getSearchPrediciton(): List<String>?
}

interface RecipeApi {
    @GET("search")
    suspend fun getRecipes(
        @Header("type") type: String,
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Int = 1
    ): RawResult
    @GET("random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int = 1
    ): RawResult
    @GET("bulk")
    suspend fun getBulkRecipe(
        @Header("type") type: String,
        @Query("apiKey") apiKey: String,
        @Query("ids") ids: String
    ): List<ExtendedRecipe>

    @GET("prediction")
    suspend fun getPrediction(
        @Header("type") type:String,
        @Query("apiKey") apiKey: String,
        @Query("text") text:String
    ): List<String>
}
