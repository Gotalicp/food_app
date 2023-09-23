package com.example.food_app.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HtmlRetrofitService : HtmlService {
    companion object {
        const val API_KEY = ""
        const val API_HOST = "https://api.spoonacular.com/"

        private var html: HtmlRetrofitService? = null
        fun getHtml() = html ?: HtmlRetrofitService()
    }

    private val htmlApi: HtmlApiService

    init {
        val htmlRetrofit = Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        htmlApi = htmlRetrofit.create(HtmlApiService::class.java)
    }

    override suspend fun getTasteWidget(id: Int): String {
        Log.d("test", "started")
        return suspendCoroutine { continuation ->
            val call: Call<String> = htmlApi.getTasteWidget(id, API_KEY)
            Log.d("test", "called")
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Log.d("test", "successfull")
                        val htmlContent = response.body()
                        continuation.resume(htmlContent!!) // Return the HTML content as is
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("test", "failed")
                    continuation.resumeWithException(t)
                }
            })
            Log.d("test", "$continuation")
        }
    }
}
interface HtmlApiService {
    @GET("recipes/{id}/tasteWidget")
    fun getTasteWidget(@Path("id") id: Int, @Query("apiKey") apiKey: String):Call<String>
}