package com.example.housesharing.kitchen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Kitchen
import com.example.housesharing.data.KitchenResponse
import com.example.housesharing.data.source.KitchenRepository

class KitchenViewModel(private val repository: KitchenRepository = KitchenRepository()): ViewModel() {

    var listHoursLiveData = MutableLiveData<ArrayList<Kitchen>>()
    var listHours = ArrayList<Kitchen>()

    init {
        createList()
    }

    private fun createList(){
        listHours.clear()
        var id = 0
        for(hour in 7..22){
            listHours.add(Kitchen(id, hour, 0, hour, 30))
            id++
            listHours.add(Kitchen(id, hour, 30, hour + 1, 0))
            id++
        }
        listHoursLiveData.value = listHours

    }

    fun createReservation(date: String, kitchen: Kitchen): LiveData<Boolean> {
        return repository.createReservation(date, kitchen)
    }

    fun fetchReservations(date: String): LiveData<KitchenResponse>{
        return repository.fetchReservations(date)
    }

    fun updateList(reservations: ArrayList<Kitchen> ){
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
        listHoursLiveData.value = listHours
    }

}