package com.example.food_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val fireBaseViewModel: FireBaseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fireBaseViewModel.acc.observe(this) {
            if(fireBaseViewModel.availableUser()){
                setContentView(R.layout.activity_app)
                val navController = findNavController(R.id.nav_host_fragment)
                val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
                bottomNav?.setupWithNavController(navController)
            }else{
                setContentView(R.layout.activity_login)
            }
        }
//        setContentView(R.layout.activity_app)
//        val navController = findNavController(R.id.nav_host_fragment)
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNav?.setupWithNavController(navController)
//        fireBaseViewModel.acc.observe(this) {
//            if (fireBaseViewModel.availableUser()) {
//                startActivity(Intent(this, LoginActivity::class.java))
//                finish()
//            }
//        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
