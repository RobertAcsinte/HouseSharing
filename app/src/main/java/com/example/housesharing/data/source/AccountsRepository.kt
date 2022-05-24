package com.example.housesharing.data.source

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.Account
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AccountsRepository() {
    private val rootRef: FirebaseDatabase = Firebase.database
    private val accountRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)


    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _accountInfoMutableLiveData = MutableLiveData<Account>()
    val accountInfoMutableLiveData: LiveData<Account>
        get() = _accountInfoMutableLiveData

    private val _userMutableLiveData = MutableLiveData<FirebaseUser>()
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private val _loggedOutMutableLiveData = MutableLiveData<Boolean>()
    val loggedOutMutableLiveData: LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    init {
        accountRef.keepSynced(true)

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

    fun login(email: String, password: String): MutableLiveData<AccountResponse> {
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
                    Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
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

    fun accountData() : MutableLiveData<AccountResponse> {
        val mutableLiveData = MutableLiveData<AccountResponse>()
        accountRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            val response = AccountResponse()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.account = Account()
                    response.account?.email = it.child("email").value.toString()
                    response.account?.firstName = it.child("firstName").value.toString()
                    response.account?.lastName = it.child("lastName").value.toString()
                    response.account?.houseId = it.child("houseId").value.toString()
                }
            }
            else{
                response.exception = task.exception
            }
            mutableLiveData.value = response
        }
        return mutableLiveData
    }

    fun fetchAccount() {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<Account>()
                _accountInfoMutableLiveData.value = user!!
            }

            override fun onCancelled(error: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        }
        accountRef.child(firebaseAuth.uid!!).addValueEventListener(listener)
    }

    fun updateFirstName(firstName: String): MutableLiveData<Boolean>{
        val mutableLiveData = MutableLiveData<Boolean>()
        accountRef.child(firebaseAuth.uid.toString()).child("firstName").setValue(firstName).addOnCompleteListener {
            if(it.isSuccessful){
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

    fun updateLastName(lastName: String): MutableLiveData<Boolean>{
        val mutableLiveData = MutableLiveData<Boolean>()
        accountRef.child(firebaseAuth.uid.toString()).child("lastName").setValue(lastName).addOnCompleteListener {
            if(it.isSuccessful){
                mutableLiveData.value = true
            }
        }
        return mutableLiveData
    }

    fun updateEmail(email: String): MutableLiveData<Exception>{
        val mutableLiveData = MutableLiveData<Exception>()
        firebaseAuth.currentUser!!.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful){
                accountRef.child(firebaseAuth.uid.toString()).child("email").setValue(email).addOnCompleteListener {
                    mutableLiveData.value = it.exception
                }
            }
            else{
                mutableLiveData.value = task.exception
            }
        }
        return mutableLiveData
    }

}