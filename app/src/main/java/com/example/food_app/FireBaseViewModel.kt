package com.example.food_app

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.Account
import com.example.food_app.data.ExtendedRecipe
import com.example.food_app.data.FireBaseAdapter
import com.example.food_app.data.FireFavDataBaseAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class FireBaseViewModel: ViewModel() {
    private var currentUser = Firebase.auth.currentUser

    private val fireBaseAdapter = FireBaseAdapter()
    private val fireFavDataBaseAdapter = FireFavDataBaseAdapter()

    private val _acc = MutableLiveData<Account>()
    val acc: LiveData<Account> get() = _acc

    private val _logged = MutableLiveData<Boolean>()
    val logged: LiveData<Boolean> get() = _logged

    init {
        if(currentUser==null){
            _acc.value = Account(null,null,null)
            _logged.value = false
        } else {
         _acc.value= currentUser.let { fireBaseAdapter.adapt(it) }
            _logged.value = true
        }
    }
    fun emptyUser() :Boolean{
        if (currentUser == null) {
            return true
        }
        return false
    }
    fun createAcc(email: String , password: String , context: View){
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _logged.value= true
                    updateAcc(currentUser.let { fireBaseAdapter.adapt(it)!! })
                    Snackbar.make(
                        context,
                        "Register success.",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                } else {
                    updateAcc(Account(null,null,null))
                    Snackbar.make(
                        context,
                        "Register failed.",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    fun logIn(email: String, password: String, context: View) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _logged.value=true
                    updateAcc(currentUser.let { fireBaseAdapter.adapt(it)!! })
                    Snackbar.make(
                        context,
                        "Login success.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    updateAcc(Account(null,null,null))
                    Snackbar.make(
                        context,
                        "Login failed.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    logout()
                }
            }
    }
    private fun updateAcc(it: Account){
        _acc.value = it
    }
    fun logout(){
        Firebase.auth.signOut()
        updateAcc(Account(null,null,null))
        _logged.value=false
    }
    fun getFav(callback: (MutableList<ExtendedRecipe>) -> Unit) {
        var uid = Firebase.auth.currentUser?.uid
        Firebase.database.getReference("user/${uid}").get[().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot != null) {
                val result = fireFavDataBaseAdapter.adapt(documentSnapshot)
                Log.d("firebase"," text $result")
                callback(result ?: mutableListOf())
            } else {
                callback(mutableListOf())
            }
        }.addOnFailureListener { exception ->
            Log.e("firebase", "Error getting data", exception)
            callback(mutableListOf())
        }
    }
    fun uploadFav(list: MutableList<ExtendedRecipe>){
        var uid = Firebase.auth.currentUser?.uid
        Firebase.database.getReference("user/${uid}").setValue(list)
    }
}
