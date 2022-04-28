package com.example.housesharing.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.model.Account
import com.example.housesharing.model.AccountResponse
import com.example.housesharing.model.Note
import com.example.housesharing.model.NoteResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AccountsRepository() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private val _loggedOutMutableLiveData = MutableLiveData<Boolean>()
    val loggedOutMutableLiveData: LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    init {
        if(firebaseAuth.currentUser != null){
            _userMutableLiveData.value = firebaseAuth.currentUser
            _loggedOutMutableLiveData.value = false
        }
    }

    fun register(email: String, firstName: String, lastName: String, password: String): MutableLiveData<AccountResponse>{
        val mutableLiveData = MutableLiveData<AccountResponse>()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                val response = AccountResponse()
                if (task.isSuccessful) {
                    _userMutableLiveData.value = firebaseAuth.currentUser
                    val result = task.result
                    result?.let {
                        response.account = Account()
                        response.account?.email = it.user?.email
                        response.account?.firstName = firstName
                        response.account?.lastName = lastName
                        registerDb(it.user?.uid, response)
                    }
                }
                else {
                    response.exception = task.exception
                }
                mutableLiveData.value = response
            }
        return mutableLiveData
    }

    private fun registerDb(uid: String?, response: AccountResponse) {
        val database = Firebase.database
        val myRef = database.getReference("Users")
        myRef.child(uid!!).setValue(response.account)
    }

    fun login(email: String, password: String): MutableLiveData<AccountResponse>{
        val mutableLiveData = MutableLiveData<AccountResponse>()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                val response = AccountResponse()
                if (task.isSuccessful) {
                    _userMutableLiveData.value = firebaseAuth.currentUser
                    val result = task.result
                    result?.let {
                        response.account = Account()
                        response.account?.email = it.user?.email
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    response.exception = task.exception
                }
                mutableLiveData.value = response
            }
        return mutableLiveData
    }

    fun logOut(){
        firebaseAuth.signOut()
        _loggedOutMutableLiveData.value = true
    }
}