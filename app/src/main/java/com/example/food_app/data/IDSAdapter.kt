package com.example.food_app.data

import com.example.food_app.common.Adapter

class IDSAdapter : Adapter<List<Int>?, String> {
    override fun adapt(t: List<Int>?): String? {
        if (t != null) {
            return t.joinToString(separator = ",")
        }
        return null
    }
}