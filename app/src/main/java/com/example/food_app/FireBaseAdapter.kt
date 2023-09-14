package com.example.food_app

import android.app.Application
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FireBaseViewModel (application:Application): AndroidViewModel(application){

    lateinit var auth: FirebaseAuth
    val currentUser = auth.currentUser

    private val _acc = MutableLiveData<Accounts>()
    val acc: LiveData<Accounts> get() = _acc

    fun checkUser() :Boolean{
        if (currentUser == null) {
            return false
        }
        return true
    }

    fun createAcc(email: String , password: String , context: View){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    updateAcc(null,email,null)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Snackbar.make(
                        context,
                        "Authentication failed.",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                    updateAcc(null,null,null)
                }
            }
    }
    fun logIn(email: String , password: String,context: View) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    updateAcc(null,email,null)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Snackbar.make(
                        context,
                        "Authentication failed.",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                    updateAcc(null,null,null)
                }
            }
    }

    fun updateAcc(username: String?, email: String?, picture: Bitmap?){
        _acc.postValue(Accounts(username,email,picture))
    }
}
