package com.example.food_app.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.food_app.FireBaseViewModel
import com.example.food_app.MainActivity
import com.example.food_app.R

class LoginActivity: AppCompatActivity() {

    private val fireBaseViewModel: FireBaseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fireBaseViewModel.logged.observe(this) {
            Log.d("pog","${fireBaseViewModel.acc}")
            if(it==true){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}