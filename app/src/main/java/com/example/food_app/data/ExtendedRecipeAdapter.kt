package com.example.food_app.data

import com.example.food_app.common.Adapter

class ExtendedRecipeAdapter : Adapter<ExtendedRecipe, ExtendedRecipe> {
    override fun adapt(t: ExtendedRecipe): ExtendedRecipe? {
        return if(t.title == null){
            null
        }else{
            t
        }
    }
}