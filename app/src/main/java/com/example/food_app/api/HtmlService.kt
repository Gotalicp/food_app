package com.example.food_app.api

interface HtmlService {
    suspend fun getTasteWidget(id: Int): String
}