package com.example.food_app.data

data class RawResult(
    val results: List<ExtendedRecipe?> = emptyList(),
    val offset: Int? = 0,
    val number: Int? = 0,
    val totalResults: Int? = 0,
    val recipes: List<ExtendedRecipe>? = emptyList()
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
data class ExtendedRecipe(
    val id: Int?=null,
    val title: String?=null,
    val image: String?=null,
    val readyInMinutes: String?=null,
    val extendedIngredients: List<Ingredient>?= mutableListOf(),
    val summary: String?=null,
    var analyzedRecipe:List<AnalyzedRecipe>?=null,
    var isAnalyzed: Boolean = false,
    var isFavoured: Boolean = false,
    var isLiked: Boolean = false,
    var likes: Int = 0
)
data class AnalyzedRecipe(
    val name:String?=null,
    val steps: List<Step>
)
data class Step(
    val number: Int?=null,
    val step: String?=null,
)
