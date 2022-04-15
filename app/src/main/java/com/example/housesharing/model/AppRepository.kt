package com.example.housesharing.model

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AppRepository(application: Application) {
    private var application: Application = application
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

    fun register(email: String, password: String){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    _userMutableLiveData.value = firebaseAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(application, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun login(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    _userMutableLiveData.value = firebaseAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(application, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logOut(){
        firebaseAuth.signOut()
        _loggedOutMutableLiveData.value = true
    }
}