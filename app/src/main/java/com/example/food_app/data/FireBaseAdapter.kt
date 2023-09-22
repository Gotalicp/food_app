package com.example.food_app.data

import com.example.food_app.common.Adapter
import com.google.firebase.auth.FirebaseUser

class FireBaseAdapter: Adapter< FirebaseUser?, Account> {
    override fun adapt(t: FirebaseUser?): Account? {
        return if(t==null){
            Account(null,null,null)
            }else{
            t?.let {
                return Account(it.displayName,it.email,null)
            }
        }
    }
}