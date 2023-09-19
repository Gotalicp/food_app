package com.example.food_app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.food_app.data.Comments
import com.example.food_app.data.FireBaseUsers
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FireDataBaseViewModel (application: Application): AndroidViewModel(application){
    private val database = Firebase.database
    private val uid = Firebase.auth.uid

    private val userRef = database.getReference("users/$uid")

    var cache:FireBaseUsers? = null

    init {
        readData()
    }

    private fun readData(){
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userProfile = dataSnapshot.getValue(FireBaseUsers::class.java)
                if (userProfile != null) {
                     cache = userProfile
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun changeData(){
        userRef.setValue(cache)
    }

    fun addFav(newFav:Int){
        cache?.favs?.add(newFav)
        changeData()
    }
    fun addLike(newLike:Int){
        cache?.likes?.add(newLike)
        changeData()
    }
    fun addComment(newComment: Comments){
        cache?.comments?.add(newComment)
        changeData()
    }
}