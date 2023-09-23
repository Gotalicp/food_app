package com.example.food_app.trivia

import android.app.Application
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.food_app.getApiService
import kotlinx.coroutines.launch

class TriviaViewModel (application: Application): AndroidViewModel(application){
    private val apiService = application.getApiService()

    private val _text = MutableLiveData<SpannableString>()
    val text: LiveData<SpannableString> get() = _text

    fun getTrivia(){
        viewModelScope.launch {
            val temp = apiService.getRandomTrivia()
            val list = apiService.getTexts(temp.text!!).map { word ->
                ClickableWord(word.annotation) { clickedWord ->
                    Log.d("word","$clickedWord")
                }
            }
            val spannableString = SpannableString(temp.text)
            for (clickableWord in list) {
                val startIndex = temp.text.indexOf(clickableWord.word)
                if (startIndex != -1) {
                    val endIndex = startIndex + clickableWord.word.length

                    val clickableSpan = object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            clickableWord.onClickAction(clickableWord.word)
                        }
                    }
                    spannableString.setSpan(
                        clickableSpan,
                        startIndex,
                        endIndex,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            _text.postValue(spannableString)
        }
    }
    fun getJoke(){
        viewModelScope.launch {
            val temp = apiService.getRandomJoke()
            val list = apiService.getTexts(temp.text!!).map { word ->
                ClickableWord(word.annotation) { clickedWord ->
                    Log.d("word","$clickedWord")
                }
            }
            val spannableString = SpannableString(temp.text)
            for (clickableWord in list) {
                val startIndex = temp.text.indexOf(clickableWord.word)
                if (startIndex != -1) {
                    val endIndex = startIndex + clickableWord.word.length

                    val clickableSpan = object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            clickableWord.onClickAction(clickableWord.word)
                        }
                    }

                    spannableString.setSpan(
                        clickableSpan,
                        startIndex,
                        endIndex,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            _text.postValue(spannableString)
        }
    }
data class ClickableWord(val word: String, val onClickAction: (String) -> Unit)

}