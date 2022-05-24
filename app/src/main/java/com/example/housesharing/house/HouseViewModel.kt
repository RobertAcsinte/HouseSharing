package com.example.housesharing.house

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Account
import com.example.housesharing.data.House
import com.example.housesharing.data.source.HouseRepository
import com.example.housesharing.data.source.KitchenRepository

class HouseViewModel (private val repository: HouseRepository = HouseRepository()): ViewModel() {

    private var _houseInfoMutableLiveData = repository.houseInfoMutableLiveData
    val houseInfoMutableLiveData: LiveData<House>
        get() = _houseInfoMutableLiveData

    fun fetchHouse(){
        repository.fetchHouse()
    }
}