package com.example.food_app.api

import com.example.food_app.data.Recipe
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
class RetrofitSpoonacular {
    companion object {
        const val API_KEY = "fe492119566446658e59d2c5d43876ef"
        const val API_HOST = "https://api.spoonacular.com/recipes/"

        private var api: RetrofitSpoonacular? = null
        fun getApi() = api ?: RetrofitSpoonacular()
    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        recipeApi = retrofit.create()
    }

//    override suspend fun getRecipesByComplexSearch(query: String): List<Recipe>
//    override suspend fun getRandomRecipe(): Recipe?
}

interface RecipeApi {
    @GET("search")
    suspend fun getRecipes(@Query("apiKey") apiKey: String,
                           @Query("query") query: String,
                           @Query("number") number: Int = 1): RecipeResponse
    @GET("random")
    suspend fun getRandomRecipe(@Query("apiKey") apiKey: String,
                                @Query("number") number: Int = 1): RecipeResponse
}