package com.example.food_app.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel(){

    private val _history = MutableLiveData<List<String>>    ()
    val history: LiveData<List<String>> get() = _history
}