package com.example.housesharing.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.HouseResponse
import com.example.housesharing.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class HouseRepository {

    private val rootRef: FirebaseDatabase = Firebase.database
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)
    private val userRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    lateinit var houseId: String

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun joinHouse(houseId: String): MutableLiveData<Boolean>{
        val mutableLiveData = MutableLiveData<Boolean>()
        houseRef.child(houseId).get().addOnSuccessListener {
            if(it.value != null){
                houseRef.child(houseId).child("members").child(firebaseAuth.uid!!).setValue(true)
                userRef.child(firebaseAuth.uid!!).child("houseId").setValue(houseId)
                mutableLiveData.value = true
            }
            else{
                mutableLiveData.value = false
            }
        }
        return mutableLiveData
    }

    fun createHouse(houseName: String): MutableLiveData<HouseResponse>{
        val mutableLiveData = MutableLiveData<HouseResponse>()
        var generatedKey = houseRef.push().key
        if (generatedKey != null) {
            houseId = generatedKey
        }
        val response = HouseResponse()
        houseRef.child(generatedKey!!).child("members").child(firebaseAuth.uid!!).setValue(true).addOnFailureListener {
            response.exception = it
        }
        houseRef.child(generatedKey!!).child("name").setValue(houseName).addOnFailureListener{
            response.exception = it
        }
        userRef.child(firebaseAuth.uid!!).child("houseId").setValue(generatedKey).addOnFailureListener {
            response.exception = it
        }
        mutableLiveData.value = response

        return mutableLiveData
    }

    fun createKitchen(houseId: String){
        houseRef.child(houseId).child("thisWeek").child("monday")
    }
}