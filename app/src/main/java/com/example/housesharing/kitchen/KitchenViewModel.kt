package com.example.housesharing.kitchen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.HouseResponse
import com.example.housesharing.data.source.HouseRepository
import com.google.firebase.auth.FirebaseUser

class KitchenViewModel(private val repository: HouseRepository = HouseRepository()): ViewModel() {



    var selectedWeek = MutableLiveData<String>()



    fun createHouse(name: String): LiveData<HouseResponse> {
        return repository.createHouse(name)
    }
}