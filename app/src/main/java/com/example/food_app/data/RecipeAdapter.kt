package com.example.food_app.data

import com.example.food_app.common.Adapter
class RecipeAdapter : Adapter<Recipe, Recipe>  {
    override fun adapt(t: Recipe): Recipe? {
        return if(t.imageUrl == null || t.title == null){
            null
        }else{
            Recipe(t.id, t.title, t.imageUrl)
        }
    }
}