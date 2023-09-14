package com.example.food_app

import android.graphics.Bitmap

data class Accounts(
    val username: String?,
    val email: String?,
    val image: Bitmap?,
)

class AccountBuilder(
    var username:String? = null,
    var email: String? = null,
    var image: Bitmap? = null,
    ){

    fun build(): Accounts {
        return Accounts(
            username!!,
            email!!,
            image!!
        )
    }
}