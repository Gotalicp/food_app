package com.example.food_app.data

data class RawResult(
    val results: List<ExtendedRecipe?> = emptyList(),
    val offset: Int? = 0,
    val number: Int? = 0,
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
    var analyzedRecipe:MutableList<AnalyzedRecipe>?=null,
    var isLiked: Boolean = false,
    var likes: Int = 0,
    var tasteWidget:String? = null
)
data class AnalyzedRecipe(
    val steps: MutableList<Step>
)
data class Step(
    val number: Int?=null,
    val step: String?=null,
)

data class Annotation(
    val annotation: String,
    val image: String,
    val tag: String
)

data class ApiAnnotations(
    var annotations: List<Annotation>
)
data class ApiText(
    val text: String=""
)
data class Trivia(
    val text: String="",
    var annotations: List<Annotation>
)

