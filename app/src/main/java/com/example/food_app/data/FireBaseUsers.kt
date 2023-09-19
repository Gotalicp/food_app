package com.example.food_app.data

data class FireBaseUsers(
    val favs: MutableList<Int>?,
    val likes: MutableList<Int>?,
    val comments: MutableList<Comments>?
)

data class Comments(
    val id: Int,
    val likes: Int?,
    val comments: Comments?,
)