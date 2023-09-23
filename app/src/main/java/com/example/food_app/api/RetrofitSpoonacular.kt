package com.example.food_app.api

import android.util.Log
import com.example.food_app.FireBaseViewModel
import com.example.food_app.data.AnalyzedRecipe
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.FoodItem
import com.example.food_app.data.IDSAdapter
import com.example.food_app.data.Predict
import com.example.food_app.data.PredictionAdapter
import com.example.food_app.data.RandomFoodTriviaResponse
import com.example.food_app.data.RawResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitSpoonacular: RetrofitService {
    companion object {
        //        const val API_KEY = "ce49337c976545478fececd91b626e81"
//        const val API_KEY = "da56f65f2b7945b3ac1425493af40362"
        const val API_KEY = "97527762c7fa4c229e509e9dddfc36d5"
        const val API_HOST = "https://api.spoonacular.com/"

        private var api: RetrofitSpoonacular? = null

        fun getApi() = api ?: RetrofitSpoonacular()
        private var predictionAdapter = PredictionAdapter()
        private val iDSAdapter = IDSAdapter()
        private val fireBaseViewModel = FireBaseViewModel()

    }

    private val cache = Cache()

    inner class Cache {
        private var recipeCache = mutableListOf<ExtendedRecipe>()
        private var favCache = mutableListOf<ExtendedRecipe>()

        fun initCache() {
            CoroutineScope(Dispatchers.Default).launch {
                fireBaseViewModel.getFav {
                    recipeCache.addAll(it)
                    favCache.addAll(it)
                }
            }
        }

        suspend fun getTheRecipes(id: Int) =
            recipeCache.find { it.id == id } ?: recipeApi.getTheRecipe(id, API_KEY)
                .apply { recipeCache.add(this) }

        suspend fun getAnalyzedRecipe(recipe: ExtendedRecipe) =
            recipeCache.find { it.id == recipe.id && it.analyzedRecipe == recipe.analyzedRecipe && !it.analyzedRecipe.isNullOrEmpty() }
                ?: recipe.apply {
                    this.analyzedRecipe = recipeApi.getAnalyzedRecipe(recipe.id!!, API_KEY)
                }.also { updateCache(recipe) }

        private fun updateCache(recipe: ExtendedRecipe) {
            recipeCache.find { it.id == recipe.id }
                .apply { recipeCache[(recipeCache.indexOf(this))] = recipe } ?: recipeCache.add(
                recipe
            )
        }

        suspend fun getFavourite(): MutableList<ExtendedRecipe> = favCache
        fun checkInFav(recipe: ExtendedRecipe) = recipe in favCache
        fun removeFromFav(recipe: ExtendedRecipe) {
            favCache.remove(recipe)
            fireBaseViewModel.uploadFav(favCache)
        }

        fun addToFav(recipe: ExtendedRecipe) {
            favCache.add(recipe)
            fireBaseViewModel.uploadFav(favCache)
        }

        fun killFav() {
            favCache = mutableListOf()
        }
    }

    private val recipeApi: RecipeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        recipeApi = retrofit.create()
    }

    override suspend fun getRecipesByComplexSearch(
        query: String,
        offset: Int
    ): List<ExtendedRecipe> = recipeApi.getRecipes(API_KEY, query, offset).results.mapNotNull { it }

    override suspend fun getRandomRecipes(number: Int): List<ExtendedRecipe>? =
        recipeApi.getRandomRecipe(API_KEY, number).recipes?.map { it }

    override suspend fun getTheRecipes(id: Int): ExtendedRecipe = cache.getTheRecipes(id)
    override suspend fun getBulkRecipes(ids: List<Int>): List<ExtendedRecipe> =
        recipeApi.getBulkRecipe(API_KEY, iDSAdapter.adapt(ids)!!)

    override suspend fun getSearchPrediction(query: String): List<String> =
        recipeApi.getPrediction(API_KEY, query).mapNotNull { predictionAdapter.adapt(it) }

    override suspend fun getAnalyzedRecipe(recipe: ExtendedRecipe): ExtendedRecipe =
        cache.getAnalyzedRecipe(recipe)

    override suspend fun getTexts(text: String) = recipeApi.getTexts(API_KEY, text)
    override suspend fun getRandomJoke() = recipeApi.getRandomJoke(API_KEY)
    override suspend fun getRandomTrivia() = recipeApi.getRandomTrivia(API_KEY)
    suspend fun getFavourite(): List<ExtendedRecipe> = cache.getFavourite()
    fun checkInFav(recipe: ExtendedRecipe): Boolean = cache.checkInFav(recipe)
    fun removeFromFav(recipe: ExtendedRecipe) = cache.removeFromFav(recipe)
    fun addToFav(recipe: ExtendedRecipe) = cache.addToFav(recipe)
    fun killFav() = cache.killFav()
    fun initCache() = cache.initCache()
}
interface RecipeApi {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("offset") offset: Int,
        @Query("number") number: Int = 6
    ): RawResult
    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int
    ): RawResult
    @GET("recipes/{id}/information")
    suspend fun getTheRecipe(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): ExtendedRecipe
    @GET("recipes/informationBulk")
    suspend fun getBulkRecipe(
        @Query("apiKey") apiKey: String,
        @Query("ids") ids: String
    ): List<ExtendedRecipe>

    @GET("recipes/autocomplete")
    suspend fun getPrediction(
        @Query("apiKey") apiKey: String,
        @Query("query") query:String,
        @Query("number") number:Int = 5
    ): List<Predict>

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getAnalyzedRecipe(
        @Path("id") query: Int,
        @Query("apiKey") apiKey: String
    ): MutableList<AnalyzedRecipe>
    @GET("food/detect")
    suspend fun getTexts(
        @Query("apiKey") apiKey: String,
        @Query("text") text: String
        ): MutableList<FoodItem>
    @GET("food/trivia/random")
    suspend fun getRandomTrivia(
        @Query("apiKey") apiKey: String
    ): RandomFoodTriviaResponse
    @GET("food/jokes/random")
    suspend fun getRandomJoke(
        @Query("apiKey") apiKey: String
    ): RandomFoodTriviaResponse
}