package com.example.food_app.data

data class RawResult(
    val results: List<Recipe?> = emptyList(),
    val offset: Int? = 0,
    val number: Int? = 0,
    val totalResults: Int? = 0,
    val recipes: List<ExtendedRecipe>? = emptyList()
)

data class Recipe(
    val id: Int?=null,
    val title: String?= null,
    val image: String?=null
)
data class Predict(
    val id: Int?=null,
    val title: String?=null
)
data class Ingredient(
    val id: Int?=null,
    val name: String?=null,
    val image: String?,
    val consistency: String?=null,
    val amount: Float?=null,
    val unit: String?=null,
    val original : String?=null
)
data class Metric(
    val amount: Float?=null,
    val unitShort: String?=null
)
data class ExtendedRecipe(
    val id: Int?=null,
    val title: String?=null,
    val image: String?=null,
    val readyInMinutes: String?=null,
    val extendedIngredients: List<Ingredient>?= mutableListOf(),
    val summary: String?=null,
    var analyzedRecipe:AnalyzedRecipe?=null
)
data class AnalyzedRecipe(
    val name:String?=null,
    val steps: List<Pair<Int,String>>
)