package com.example.food_app.data

import com.example.food_app.common.Adapter
import com.google.firebase.auth.FirebaseUser

class FireBaseAdapter: Adapter< FirebaseUser?, Accounts> {
    override fun adapt(t: FirebaseUser?): Accounts? {
        return if(t==null){
            Accounts(null,null,null)
            }else{
            t?.let {
                return Accounts(it.displayName,it.email,null)
            }
        }
    }
}