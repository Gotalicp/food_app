package com.example.food_app

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.food_app.data.FireBaseAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FireBaseViewModel (application:Application): AndroidViewModel(application){

    private var currentUser = Firebase.auth.currentUser

    private val _acc = MutableLiveData<Accounts>()
    val acc: LiveData<Accounts> get() = _acc

    private val fireBaseAdapter = FireBaseAdapter()

    init {
        _acc.value = currentUser.let { fireBaseAdapter.adapt(it) }
    }

    fun availableUser() :Boolean{
        if (currentUser == null) {
            return false
        }
        return true
    }

    fun createAcc(email: String , password: String , context: View){
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    updateAcc(currentUser.let { fireBaseAdapter.adapt(it)!! })
                    Snackbar.make(
                        context,
                        "Authentication success.",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                } else {
                    updateAcc(Accounts(null,null,null))
                    Snackbar.make(
                        context,
                        "Authentication failed.",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    fun logIn(email: String, password: String, context: View) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateAcc(currentUser.let { fireBaseAdapter.adapt(it)!! })
                    Snackbar.make(
                        context,
                        "Authentication success.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    updateAcc(Accounts(null,null,null))
                    Snackbar.make(
                        context,
                        "Authentication failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    logout()
                }
            }
    }

    private fun updateAcc(it: Accounts){
        _acc.value = it
    }

    fun logout(){
        Firebase.auth.signOut()
        updateAcc(Accounts(null,null,null))
    }
}
