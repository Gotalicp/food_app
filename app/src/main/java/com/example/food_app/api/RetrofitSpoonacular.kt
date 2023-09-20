package com.example.food_app.api

import android.util.Log
import com.example.food_app.data.AnalyzedRecipe
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.ExtendedRecipeAdapter
import com.example.food_app.data.Predict
import com.example.food_app.data.PredictionAdapter
import com.example.food_app.data.RawResult
import com.example.food_app.data.Recipe
import com.example.food_app.data.RecipeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitSpoonacular: RetrofitService {
    companion object {
        const val API_KEY = "ce49337c976545478fececd91b626e81"
        const val API_HOST = "https://api.spoonacular.com/recipes/"

        private var api: RetrofitSpoonacular? = null

        fun getApi() = api  ?: RetrofitSpoonacular()
        private var recipeAdapter = RecipeAdapter()
        private var extendedRecipeAdapter = ExtendedRecipeAdapter()
        private var predictionAdapter = PredictionAdapter()

    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        recipeApi = retrofit.create()
    }

    override suspend fun getRecipesByComplexSearch(query: String, offset: Int): List<ExtendedRecipe> = recipeApi.getRecipes(API_KEY, query ,offset).results.mapNotNull { recipeAdapter.adapt(it!!) }
    override suspend fun getRandomRecipes(number: Int): List<ExtendedRecipe>? = recipeApi.getRandomRecipe(API_KEY, number).recipes?.mapNotNull { extendedRecipeAdapter.adapt(it!!) }
    override suspend fun getTheRecipes(id: Int): ExtendedRecipe? = recipeApi.getTheRecipe(API_KEY,id)
    override suspend fun getBulkRecipes(ids:String): List<ExtendedRecipe> = recipeApi.getBulkRecipe(API_KEY,ids)
    override suspend fun getSearchPrediction(query:String): List<String>? = recipeApi.getPrediction(API_KEY,query).mapNotNull { predictionAdapter.adapt(it) }
    override suspend fun getAnalyzedRecipe(recipe:ExtendedRecipe): ExtendedRecipe = recipe.apply { analyzedRecipe = recipeApi.getAnalyzedRecipe(API_KEY,recipe.id!!) }
}

interface RecipeApi {
    @GET("complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("offset") offset: Int,
        @Query("number") number: Int = 6
    ): RawResult
    @GET("random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): RawResult
    @GET("recipes/{id}/information")
    suspend fun getTheRecipe(
        @Query("apiKey") apiKey: String,
        @Path("id") id: Int
    ): ExtendedRecipe
    @GET("informationBulk")
    suspend fun getBulkRecipe(
        @Query("apiKey") apiKey: String,
        @Query("ids") ids: String
    ): List<ExtendedRecipe>

    @GET("autocomplete")
    suspend fun getPrediction(
        @Query("apiKey") apiKey: String,
        @Query("query") query:String,
        @Query("number") number:Int = 5
    ): List<Predict>

    @GET("{id}/analyzedInstructions")
    suspend fun getAnalyzedRecipe(
        @Query("apiKey") apiKey: String,
        @Path("id") query: Int,
    ): AnalyzedRecipe
}
