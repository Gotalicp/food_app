package com.example.food_app.data

import com.example.food_app.common.Adapter
import com.google.firebase.database.DataSnapshot

class FireFavDataBaseAdapter: Adapter<DataSnapshot,MutableList<ExtendedRecipe>> {
    override fun adapt(t: DataSnapshot): MutableList<ExtendedRecipe>? {
        val extendedRecipeList = mutableListOf<ExtendedRecipe>()

        for (recipeSnapshot in t.children) {
            val id = recipeSnapshot.child("id").getValue(Int::class.java)
            val title = recipeSnapshot.child("title").getValue(String::class.java)
            val image = recipeSnapshot.child("image").getValue(String::class.java)
            val readyInMinutes = recipeSnapshot.child("readyInMinutes").getValue(String::class.java)
            val extendedIngredients = mutableListOf<Ingredient>()
            val summary = recipeSnapshot.child("summary").getValue(String::class.java)
            val analyzedRecipe = mutableListOf<AnalyzedRecipe>()
            val isLiked = recipeSnapshot.child("isLiked").getValue(Boolean::class.java)
            val likes = recipeSnapshot.child("likes").getValue(Int::class.java)

            for (analysisSnapshot in recipeSnapshot.child("analyzedRecipe").children) {
                val analyzed = AnalyzedRecipe(
                    steps = mutableListOf()
                )
                for (waysSnapshot in analysisSnapshot.child("steps").children) {
                    val temp = Step(
                        number = waysSnapshot.child("number").getValue(Int::class.java),
                        step = waysSnapshot.child("step").getValue(String::class.java)
                    )
                    analyzed.steps.add(temp)
                }
                analyzedRecipe.add(analyzed)
            }

            for (ingredientSnapshot in recipeSnapshot.child("extendedIngredients").children) {
                val ingredient = Ingredient(
                    id = ingredientSnapshot.child("id").getValue(Int::class.java),
                    name = ingredientSnapshot.child("name").getValue(String::class.java),
                    image = ingredientSnapshot.child("image").getValue(String::class.java),
                    consistency = ingredientSnapshot.child("consistency")
                        .getValue(String::class.java),
                    amount = ingredientSnapshot.child("amount").getValue(Float::class.java),
                    unit = ingredientSnapshot.child("unit").getValue(String::class.java),
                    original = ingredientSnapshot.child("original").getValue(String::class.java)
                )
                extendedIngredients.add(ingredient)
            }

            val recipe = ExtendedRecipe(
                id = id,
                title = title,
                image = image,
                readyInMinutes = readyInMinutes,
                summary = summary,
                extendedIngredients = extendedIngredients,
                analyzedRecipe = analyzedRecipe,
                isLiked = isLiked ?: false,
                likes = likes ?: 0
            )

            extendedRecipeList.add(recipe)
        }

        return extendedRecipeList
    }
}