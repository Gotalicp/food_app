package com.example.food_app.data

import com.example.food_app.common.Adapter
import com.google.firebase.database.DataSnapshot

class FireFavDataBaseAdapter: Adapter<DataSnapshot,MutableList<ExtendedRecipe>> {
    override fun adapt(t: DataSnapshot): MutableList<ExtendedRecipe>? {
        val extendedRecipeList = mutableListOf<ExtendedRecipe>()

        for (recipeSnapshot in t.children) {
            val id = recipeSnapshot.child("id").getValue(Int::class.java) // Assuming the ID is stored as the key
            val title = recipeSnapshot.child("title").getValue(String::class.java)
            val image = recipeSnapshot.child("image").getValue(String::class.java)
            val readyInMinutes = recipeSnapshot.child("readyInMinutes").getValue(String::class.java)
            val summary = recipeSnapshot.child("summary").getValue(String::class.java)
            val extendedIngredients = mutableListOf<Ingredient>()

            for (ingredientSnapshot in recipeSnapshot.child("extendedIngredients").children) {
                val ingredient = Ingredient(
                    image = ingredientSnapshot.child("image").getValue(String::class.java),
                    amount = ingredientSnapshot.child("amount").getValue(Float::class.java),
                    unit = ingredientSnapshot.child("unit").getValue(String::class.java),
                    original = ingredientSnapshot.child("original").getValue(String::class.java),
                    name = ingredientSnapshot.child("name").getValue(String::class.java),
                    id = ingredientSnapshot.child("id").getValue(Int::class.java),
                    consistency = ingredientSnapshot.child("consistency").getValue(String::class.java)
                )
                extendedIngredients.add(ingredient)
            }

            val recipe = ExtendedRecipe(
                id = id,
                title = title,
                image = image,
                readyInMinutes = readyInMinutes,
                summary = summary,
                extendedIngredients = extendedIngredients
            )

            extendedRecipeList.add(recipe)
        }

        return extendedRecipeList
    }
}