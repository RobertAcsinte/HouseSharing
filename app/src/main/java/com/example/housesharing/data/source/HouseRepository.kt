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



class HouseRepository {

    private val rootRef: FirebaseDatabase = Firebase.database
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)
    private val userRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    lateinit var houseId: String

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _houseInfoMutableLiveData = MutableLiveData<House>()
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


    fun fetchHouse() {
        val response = HouseResponse()

        //Get the house ID of the logged user
        val listenerUser = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<Account>()

                //Get the house name and the id's of the members
                val listenerHouse = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        response.house = House()
                        response.house!!.name = snapshot.child("name").value.toString()
                        snapshot.child("members").children.forEach { userId ->

                            //Get the first name and the last name of the members
                            val listenerMembers = object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    response.house!!.members.add(snapshot.child("firstName").value.toString() + " " + snapshot.child("lastName").value.toString())
                                    _houseInfoMutableLiveData.value = response.house
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            }
                            userRef.child(userId.key.toString()).addValueEventListener(listenerMembers)

                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                }
                houseRef.child(user!!.houseId!!).addValueEventListener(listenerHouse)
            }
            override fun onCancelled(error: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", error.toException())
            }
        }
        userRef.child(firebaseAuth.uid!!).addValueEventListener(listenerUser)
    }


}