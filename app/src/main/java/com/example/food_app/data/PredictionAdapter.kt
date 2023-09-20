package com.example.food_app.data

import com.example.food_app.common.Adapter

class PredictionAdapter : Adapter<Predict, String> {
    override fun adapt(t: Predict): String? {
        return t.title
    }
}