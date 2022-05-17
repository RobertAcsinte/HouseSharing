package com.example.housesharing.kitchen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Kitchen
import com.example.housesharing.data.KitchenResponse
import com.example.housesharing.data.source.KitchenRepository

class KitchenViewModel(private val repository: KitchenRepository = KitchenRepository()): ViewModel() {

    var selectedDay = MutableLiveData<String>()
    var selectedWeek = MutableLiveData<String>()

    var thisWeekMondayLiveData = MutableLiveData<ArrayList<Kitchen>>()
    var thisWeekMonday = ArrayList<Kitchen>()

    var thisWeekTuesdayLiveData = MutableLiveData<ArrayList<Kitchen>>()
    var thisWeekTuesday = ArrayList<Kitchen>()

    var nextWeekMondayLiveData = MutableLiveData<ArrayList<Kitchen>>()
    var nextWeekMonday = ArrayList<Kitchen>()

    var nextWeekTuesdayLiveData = MutableLiveData<ArrayList<Kitchen>>()
    var nextWeekTuesday = ArrayList<Kitchen>()

    init {
        selectedDay.value = "monday"
        selectedWeek.value = "thisWeek"
        createThisWeek()
        createNextWeek()
    }

    private fun createThisWeek(){
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "00:00 - 00:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "00:30 - 01:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "01:00 - 01:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "01:30 - 02:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "02:00 - 02:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "02:30 - 03:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "03:00 - 03:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "03:30 - 04:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "04:00 - 04:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "04:30 - 05:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "05:00 - 05:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "05:30 - 06:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "06:00 - 06:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "06:30 - 07:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "07:00 - 07:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "07:30 - 08:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "08:00 - 08:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "08:30 - 09:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "09:00 - 09:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "09:30 - 10:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "10:00 - 10:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "10:30 - 11:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "11:00 - 11:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "11:30 - 12:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "12:00 - 12:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "12:30 - 13:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "13:00 - 13:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "13:30 - 14:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "14:00 - 14:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "14:30 - 15:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "15:00 - 15:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "15:30 - 16:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "16:00 - 16:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "16:30 - 17:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "17:00 - 17:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "17:30 - 18:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "18:00 - 18:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "18:30 - 19:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "19:00 - 19:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "19:30 - 20:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "20:00 - 20:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "20:30 - 21:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "21:00 - 21:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "21:30 - 22:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "22:00 - 22:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "22:30 - 23:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "23:00 - 23:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "23:30 - 00:00"))
        thisWeekMondayLiveData.value = thisWeekMonday

        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "00:00 - 00:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "00:30 - 01:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "01:00 - 01:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "01:30 - 02:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "02:00 - 02:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "02:30 - 03:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "03:00 - 03:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "03:30 - 04:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "04:00 - 04:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "04:30 - 05:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "05:00 - 05:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "05:30 - 06:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "06:00 - 06:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "06:30 - 07:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "07:00 - 07:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "07:30 - 08:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "08:00 - 08:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "08:30 - 09:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "09:00 - 09:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "09:30 - 10:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "10:00 - 10:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "10:30 - 11:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "11:00 - 11:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "11:30 - 12:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "12:00 - 12:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "12:30 - 13:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "13:00 - 13:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "13:30 - 14:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "14:00 - 14:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "14:30 - 15:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "15:00 - 15:30"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "15:30 - 16:00"))
        thisWeekTuesday.add(Kitchen("thisWeek", "monday", "16:00 - 16:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "16:30 - 17:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "17:00 - 17:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "17:30 - 18:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "18:00 - 18:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "18:30 - 19:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "19:00 - 19:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "19:30 - 20:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "20:00 - 20:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "20:30 - 21:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "21:00 - 21:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "21:30 - 22:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "22:00 - 22:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "22:30 - 23:00"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "23:00 - 23:30"))
        thisWeekMonday.add(Kitchen("thisWeek", "monday", "23:30 - 00:00"))
        thisWeekTuesdayLiveData.value = thisWeekTuesday
    }

    private fun createNextWeek(){
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "00:00 - 00:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "00:30 - 01:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "01:00 - 01:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "01:30 - 02:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "02:00 - 02:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "02:30 - 03:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "03:00 - 03:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "03:30 - 04:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "04:00 - 04:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "04:30 - 05:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "05:00 - 05:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "05:30 - 06:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "06:00 - 06:30"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "06:30 - 07:00"))
        nextWeekMonday.add(Kitchen("nextWeek", "monday", "07:00 - 07:30"))
        nextWeekMondayLiveData.value = nextWeekMonday

        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "00:00 - 00:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "00:30 - 01:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "01:00 - 01:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "01:30 - 02:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "02:00 - 02:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "02:30 - 03:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "03:00 - 03:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "03:30 - 04:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "04:00 - 04:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "04:30 - 05:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "05:00 - 05:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "05:30 - 06:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "06:00 - 06:30"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "06:30 - 07:00"))
        nextWeekTuesday.add(Kitchen("nextWeek", "tuesday", "07:00 - 07:30"))
        nextWeekTuesdayLiveData.value = nextWeekTuesday
    }

    fun createReservation(week: String, day: String, time: String): LiveData<Boolean> {
        return repository.createReservation(week, day, time)
    }

    fun fetchReservations(week: String, day: String): LiveData<KitchenResponse>{
//        var temp = ArrayList<Kitchen>()
//        temp.addAll(repository.fetchReservations(week, day).value!!.kitchen!!)
//        Log.d("SLOBOZ", temp.toString())
        return repository.fetchReservations(week, day)
    }

    fun editReservations(reservations: ArrayList<Kitchen>, day: String, week: String){
        var temp = ArrayList<Kitchen>()
        temp.addAll(reservations)

        if(day == "monday"){
            if(week == "thisWeek"){
                for(offline in thisWeekMonday){
                    for(online in temp){
                        if(offline.time == online.time){
                            offline.userId = online.userId
                        }
                    }
                }
            }
            else{
                for(offline in nextWeekMonday){
                    for(online in temp){
                        if(offline.time == online.time){
                            offline.userId = online.userId
                        }
                    }
                }
            }
        }
        if(day == "tuesday"){
            if(week == "thisWeek"){
                for(offline in thisWeekTuesday){
                    for(online in temp){
                        if(offline.time == online.time){
                            offline.userId = online.userId
                        }
                    }
                }
            }
            else{
                for(offline in nextWeekTuesday){
                    for(online in temp){
                        if(offline.time == online.time){
                            offline.userId = online.userId
                        }
                    }
                }
            }
        }
    }
}