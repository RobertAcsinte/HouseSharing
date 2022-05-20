package com.example.housesharing.bathroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Appointment
import com.example.housesharing.data.AppointmentResponse
import com.example.housesharing.data.source.BathroomRepository


class BathroomViewModel(private val repository: BathroomRepository = BathroomRepository()): ViewModel() {

    var listHoursLiveData = MutableLiveData<ArrayList<Appointment>>()
    var listHours = ArrayList<Appointment>()


    init
    {
        createList()
    }

    private fun createList(){
        listHours.clear()
        var id = 0
        for(hour in 7..22){
            listHours.add(Appointment(id, hour, 0, hour, 30))
            id++
            listHours.add(Appointment(id, hour, 30, hour + 1, 0))
            id++
        }
        listHoursLiveData.value = listHours

    }

    fun createReservation(date: String, appointment: Appointment): LiveData<Boolean> {
        return repository.createReservation(date, appointment)
    }

    fun fetchReservations(date: String): LiveData<AppointmentResponse> {
        return repository.fetchReservations(date)
    }

    fun updateList(reservations: ArrayList<Appointment>, startHour: Int){
        createList()
        listHours.forEach { offline ->
            reservations.forEach { online ->
                if(offline.id == online.id){
                    offline.userId = online.userId
                    offline.firstName = online.firstName
                    offline.lastName = online.lastName
                }
            }
        }

        var tempHours = ArrayList<Appointment>()
        listHours.forEach {
            if(it.timeStartHour!! >= startHour){
                tempHours.add(it)
            }
        }

        listHoursLiveData.value = tempHours
    }

}