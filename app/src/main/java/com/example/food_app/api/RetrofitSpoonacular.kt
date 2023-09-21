package com.example.food_app.api

import com.example.food_app.data.AnalyzedRecipe
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.IDSAdapter
import com.example.food_app.data.Predict
import com.example.food_app.data.PredictionAdapter
import com.example.food_app.data.RawResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitSpoonacular: RetrofitService {
    companion object {
//        const val API_KEY = "ce49337c976545478fececd91b626e81"
        const val API_KEY = "da56f65f2b7945b3ac1425493af40362"
        const val API_HOST = "https://api.spoonacular.com/recipes/"

        private var api: RetrofitSpoonacular? = null

        fun getApi() = api  ?: RetrofitSpoonacular()
        private var predictionAdapter = PredictionAdapter()
        private val iDSAdapter = IDSAdapter()
    }

    private val cache = Cache()
    inner class Cache {
        private var recipeCache: MutableList<ExtendedRecipe>? = mutableListOf()
        suspend fun getTheRecipes(id: Int) = recipeCache?.find{ it.id == id } ?:
        recipeApi.getTheRecipe(id, API_KEY).apply{ recipeCache?.add(this) }

        suspend fun getAnalyzedRecipe(recipe:ExtendedRecipe) = recipeCache?.find{ it.id == recipe.id && it.isAnalyzed } ?:
        recipe.apply {
            analyzedRecipe = recipeApi.getAnalyzedRecipe(recipe.id!!, API_KEY)
            isAnalyzed = true
        }.also { updateCache(recipe) }

        suspend fun updateCache(recipe : ExtendedRecipe){
            recipeCache?.find { it.id == recipe.id }.apply{ recipeCache!![(recipeCache!!.indexOf(this))] = recipe }?:
            recipeCache?.add(recipe)
        }
        fun invalidate() {
           recipeCache = null
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

    override suspend fun getRecipesByComplexSearch(query: String, offset: Int): List<ExtendedRecipe> = recipeApi.getRecipes(API_KEY, query ,offset).results.mapNotNull { it }
    override suspend fun getRandomRecipes(number: Int): List<ExtendedRecipe>? = recipeApi.getRandomRecipe(API_KEY, number).recipes?.mapNotNull { it }
    override suspend fun getTheRecipes(id: Int): ExtendedRecipe? = cache.getTheRecipes(id)
    override suspend fun getBulkRecipes(ids:List<Int>): List<ExtendedRecipe> = recipeApi.getBulkRecipe(API_KEY, iDSAdapter.adapt(ids)!!)
    override suspend fun getSearchPrediction(query:String): List<String>? = recipeApi.getPrediction(API_KEY,query).mapNotNull { predictionAdapter.adapt(it) }
    override suspend fun getAnalyzedRecipe(recipe:ExtendedRecipe): ExtendedRecipe = cache.getAnalyzedRecipe(recipe)
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
    @GET("{id}/information")
    suspend fun getTheRecipe(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
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
        @Path("id") query: Int,
        @Query("apiKey") apiKey: String
    ): List<AnalyzedRecipe>
}}