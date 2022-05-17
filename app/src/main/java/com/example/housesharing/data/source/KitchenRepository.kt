package com.example.housesharing.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.Kitchen
import com.example.housesharing.data.KitchenResponse
import com.example.housesharing.data.Note
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KitchenRepository {
    private val rootRef: FirebaseDatabase = Firebase.database
    private val notesRef: DatabaseReference = rootRef.getReference(Constants.NOTES_REF)
    private val accountRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)
    private val kitchenRef: DatabaseReference = rootRef.getReference(Constants.KITCHEN_REF)

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        notesRef.keepSynced(true)
        accountRef.keepSynced(true)
        houseRef.keepSynced(true)
    }

    fun createReservation(week: String, day: String, time: String): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        val response = NoteResponse()
        accountRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                task.result.value.toString()?.let {
                    kitchenRef.child(it).child(week).child(day).child(time).setValue(firebaseAuth.uid)
                    mutableLiveData.value = true
                }
            }
        }
        return mutableLiveData
    }

    fun fetchReservations(week: String, day: String): MutableLiveData<KitchenResponse> {
        val mutableLiveData = MutableLiveData<KitchenResponse>()
        accountRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.value.toString()?.let {
                    kitchenRef.child(it).child(week).child(day).get().addOnCompleteListener { snapshot ->
                        val response = KitchenResponse()
                        if(snapshot.isSuccessful){
                            val result = snapshot.result
                            result?.let {
                                for(d: DataSnapshot in it.children){
                                    var reservation = Kitchen(week, day, d.key.toString(), d.value.toString())
                                    response.kitchen!!.add(reservation)
                                }
                            }
                        }
                        else{
                            response.exception = task.exception
                        }
                        mutableLiveData.value = response
                    }
                }
            }
        }
        return mutableLiveData
    }
}