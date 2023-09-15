package com.example.food_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private val fireBaseViewModel: FireBaseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        fireBaseViewModel.acc.observe(this) {
            if (!fireBaseViewModel.availableUser()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}