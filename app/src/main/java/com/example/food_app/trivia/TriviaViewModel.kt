package com.example.food_app.trivia

import android.app.Application
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.food_app.R
import com.example.food_app.data.TriviaJokeAdapter
import com.example.food_app.databinding.FragmentTriviaBinding
import com.example.food_app.getApiService
import kotlinx.coroutines.launch

class TriviaViewModel (application: Application): AndroidViewModel(application){
    private val apiService = application.getApiService()
    private  val adapter = TriviaJokeAdapter()

    private val _text = MutableLiveData<SpannableString>()
    val text: LiveData<SpannableString> get() = _text

    fun getTrivia(navController:NavController){
        viewModelScope.launch {
            adapter.setOnSpanClickListener {annotation->
                Log.d("clicked", annotation)
                navController.navigate(R.id.TriviaToSearch, bundleOf("item" to annotation))
            }
            _text.postValue(adapter.adapt(apiService.getRandomTrivia()))
        }
    }
    fun getJoke(navController:NavController){
        viewModelScope.launch {
            adapter.setOnSpanClickListener {annotation->
                Log.d("clicked", annotation)
                navController.navigate(R.id.TriviaToSearch, bundleOf("item" to annotation))
            }
            _text.postValue(adapter.adapt(apiService.getRandomJoke()))
        }
    }
    fun updateScreen(binding:FragmentTriviaBinding){
        binding.title.text = "POG"
        binding.text.text = _text.value
        binding.text.movementMethod = LinkMovementMethod.getInstance()
    }
}