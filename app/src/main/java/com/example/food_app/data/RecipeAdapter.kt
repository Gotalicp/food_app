package com.example.food_app.data

import com.example.food_app.common.Adapter
class RecipeAdapter : Adapter<Recipe, ExtendedRecipe>  {
    override fun adapt(t: Recipe): ExtendedRecipe? {
        return if(t.title == null){
            null
        }else{
            ExtendedRecipe(t.id, t.title, t.image)
        }
    }
}