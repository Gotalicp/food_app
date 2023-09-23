package com.example.food_app.data

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import com.example.food_app.common.Adapter


class TriviaJokeAdapter : Adapter<Trivia, SpannableString> {
    override fun adapt(t: Trivia): SpannableString {
        Log.d("work? adapter","$t")
        val spannableString = SpannableString(t.text)

        for (annotation in t.annotations) {
            val startIndex = t.text.indexOf(annotation.annotation)
            val endIndex = startIndex + annotation.annotation.length

            if (startIndex != -1) {
                val clickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onSpanClick!!(annotation.annotation)
                    }
                }
                spannableString.setSpan(
                    clickableSpan,
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return spannableString
    }
    private var onSpanClick: ((String) -> Unit)? = null
    fun setOnSpanClickListener(listener: (String) -> Unit) {
        onSpanClick = listener
    }
}