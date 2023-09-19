package com.example.food_app.data
data class RawResult(
    val results: List<Recipe?> = emptyList(),
    val offset: Int? = 0,
    val number: Int? = 0,
    val totalResults: Int? = 0
)
data class RawRecipe(
    val id: Int,
    val title: String? = null,
    val image: String? = null,
    val imageType: String? = null
)
data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String?,
)
data class Ingredient(
    val id: Int,
    val name: String,
    val consistency: String?,
    val amount: Metric,
    val original : String?
)
data class Metric(
    val amount: Float,
    val unitShort: String
)
data class ExtendedRecipe(
    val id: Int,
    val title: String,
    val image: String?,
    val readyInMinutes: String?,
    val analyzedInstructions: String?,
    val extendedIngredients: List<Ingredient>,
    val summary: String?
)