package com.example.housesharing.data.source

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.Account
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.House
import com.example.housesharing.data.HouseResponse
import com.example.housesharing.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.Exception


class HouseRepository {

    private val rootRef: FirebaseDatabase = Firebase.database
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)
    private val userRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    lateinit var houseId: String

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _houseInfoMutableLiveData = MutableLiveData<House>()
    val houseInfoMutableLiveData: LiveData<House>
        get() = _houseInfoMutableLiveData

    init {
        houseRef.keepSynced(true)
        userRef.keepSynced(true)
    }

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


    fun houseData(): MutableLiveData<Exception>{
        val mutableLiveData = MutableLiveData<Exception>()
        var house = House()

        //get house id
        userRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            val houseId: String
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    houseId = it.child("houseId").value.toString()
                    house.id = houseId

                    //get house name
                    val listener = object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            house.name = snapshot.child("name").value.toString()
                            house.members.clear()

                            //Get the first name and the last name of the members
                            snapshot.child("members").children.forEach { userId ->
                                val listenerMembers = object : ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        house.members.add(snapshot.child("firstName").value.toString() + " " + snapshot.child("lastName").value.toString())
                                        _houseInfoMutableLiveData.value = house
                                    }
                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }
                                }
                                userRef.child(userId.key.toString()).addListenerForSingleValueEvent(listenerMembers)
                            }

                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    }
                    houseRef.child(houseId).addValueEventListener(listener)
                }
            }
            mutableLiveData.value = task.exception
        }
        return mutableLiveData
    }

    fun leaveHouse(): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        userRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    userRef.child(firebaseAuth.uid.toString()).child("houseId").setValue(null)
                    houseRef.child(it.value.toString()).child("members").setValue(null).addOnCompleteListener {
                        mutableLiveData.value = true
                    }
                }
            }
        }
        return mutableLiveData
    }


    fun changeName(name: String): MutableLiveData<Exception>{
        val mutableLiveData = MutableLiveData<Exception>()
        //get house id
        userRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                houseRef.child(task.result.child("houseId").value.toString()).child("name").setValue(name).addOnCompleteListener {
                    _houseInfoMutableLiveData.value!!.name = name
                }
            }
        }

        return mutableLiveData
    }








}