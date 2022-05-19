package com.example.housesharing.data.source

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.Appointment
import com.example.housesharing.data.AppointmentResponse
import com.example.housesharing.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KitchenRepository {
    private val rootRef: FirebaseDatabase = Firebase.database
    private val accountRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)
    private val kitchenRef: DatabaseReference = rootRef.getReference(Constants.KITCHEN_REF)

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        accountRef.keepSynced(true)
        houseRef.keepSynced(true)
        kitchenRef.keepSynced(true)
    }

        fun createReservation(date: String, appointment: Appointment): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        accountRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                task.result?.let {
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeStartHour").setValue(appointment.timeStartHour)
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeStartMinute").setValue(appointment.timeStartMinute)
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeEndHour").setValue(appointment.timeEndHour)
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeEndMinute").setValue(appointment.timeEndMinute)
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("userId").setValue(firebaseAuth.uid)
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("firstName").setValue(it.child("firstName").value.toString())
                    kitchenRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("lastName").setValue(it.child("lastName").value.toString())

                    mutableLiveData.value = true
                }
            }
        }
        return mutableLiveData
    }


    fun fetchReservations(date: String): MutableLiveData<AppointmentResponse> {
        val mutableLiveData = MutableLiveData<AppointmentResponse>()
        accountRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.value.toString()?.let {
                    kitchenRef.child(it).child(date).get().addOnCompleteListener { snapshot ->
                        val response = AppointmentResponse()
                        if(snapshot.isSuccessful){
                            val result = snapshot.result
                            result?.let { snapshot ->
                                for(d: DataSnapshot in snapshot.children){
                                    Log.d("RESERVATION", d.key.toString() + " - " + d.child("timeStartHour").value.toString())
                                    var reservation = Appointment(d.key!!.toInt(), d.child("timeStartHour").value.toString().toInt(),
                                        d.child("timeStartMinute").value.toString().toInt(), d.child("timeEndHour").value.toString().toInt(),
                                        d.child("timeEndMinute").value.toString().toInt(), d.child("userId").value.toString(),
                                        d.child("firstName").value.toString(), d.child("lastName").value.toString())
                                    response.appointment!!.add(reservation)
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

    fun fetchReservationsToday(date: String, hour: Int): MutableLiveData<AppointmentResponse> {
        val mutableLiveData = MutableLiveData<AppointmentResponse>()
        accountRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.value.toString()?.let {
                    kitchenRef.child(it).child(date).get().addOnCompleteListener { snapshot ->
                        val response = AppointmentResponse()
                        if(snapshot.isSuccessful){
                            val result = snapshot.result
                            result?.let { snapshot ->
                                for(d: DataSnapshot in snapshot.children){
                                    var reservation = Appointment(d.key!!.toInt(), d.child("timeStartHour").value.toString().toInt(),
                                        d.child("timeStartMinute").value.toString().toInt(), d.child("timeEndHour").value.toString().toInt(),
                                        d.child("timeEndMinute").value.toString().toInt(), d.child("userId").value.toString(),
                                        d.child("firstName").value.toString(), d.child("lastName").value.toString())
                                    if(reservation.timeStartHour!! >= hour){
                                        response.appointment!!.add(reservation)
                                    }
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