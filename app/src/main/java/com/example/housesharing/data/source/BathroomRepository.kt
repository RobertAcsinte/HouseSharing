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

class BathroomRepository {
    private val rootRef: FirebaseDatabase = Firebase.database
    private val accountRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)
    private val bathroomRef: DatabaseReference = rootRef.getReference(Constants.BATHROOM_REF)

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        accountRef.keepSynced(true)
        houseRef.keepSynced(true)
        bathroomRef.keepSynced(true)
    }

    fun createReservation(date: String, appointment: Appointment): MutableLiveData<Boolean> {
        val mutableLiveData = MutableLiveData<Boolean>()
        accountRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                task.result?.let {
                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeStartHour").setValue(appointment.timeStartHour)
                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeStartMinute").setValue(appointment.timeStartMinute)
                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeEndHour").setValue(appointment.timeEndHour)
                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("timeEndMinute").setValue(appointment.timeEndMinute)
                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("userId").setValue(firebaseAuth.uid)
//                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("firstName").setValue(it.child("firstName").value.toString())
//                    bathroomRef.child(it.child("houseId").value.toString()).child(date).child(appointment.id.toString()).child("lastName").setValue(it.child("lastName").value.toString())

                    mutableLiveData.value = true
                }
            }
        }
        return mutableLiveData
    }


    fun fetchReservations(date: String): MutableLiveData<AppointmentResponse> {
        val mutableLiveData = MutableLiveData<AppointmentResponse>()
        accountRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.let { user ->
                    bathroomRef.child(user.child("houseId").value.toString()).child(date).get().addOnCompleteListener { snapshot ->
                        val response = AppointmentResponse()
                        if(snapshot.isSuccessful){
                            val result = snapshot.result
                            result?.let { snapshot ->
                                for(d: DataSnapshot in snapshot.children){
                                    Log.d("RESERVATION", d.key.toString() + " - " + d.child("timeStartHour").value.toString())
                                    var reservation = Appointment(d.key!!.toInt(), d.child("timeStartHour").value.toString().toInt(),
                                        d.child("timeStartMinute").value.toString().toInt(), d.child("timeEndHour").value.toString().toInt(),
                                        d.child("timeEndMinute").value.toString().toInt(), d.child("userId").value.toString(),
                                        user.child("firstName").value.toString(), user.child("lastName").value.toString())
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
        accountRef.child(firebaseAuth.uid.toString()).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.let { user ->
                    bathroomRef.child(user.child("houseId").value.toString()).child(date).get().addOnCompleteListener { snapshot ->
                        val response = AppointmentResponse()
                        if(snapshot.isSuccessful){
                            val result = snapshot.result
                            result?.let { snapshot ->
                                for(d: DataSnapshot in snapshot.children){
                                    var reservation = Appointment(d.key!!.toInt(), d.child("timeStartHour").value.toString().toInt(),
                                        d.child("timeStartMinute").value.toString().toInt(), d.child("timeEndHour").value.toString().toInt(),
                                        d.child("timeEndMinute").value.toString().toInt(), d.child("userId").value.toString(),
                                        user.child("firstName").value.toString(), user.child("lastName").value.toString())
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