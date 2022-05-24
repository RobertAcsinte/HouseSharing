package com.example.housesharing.house

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.House
import com.example.housesharing.data.source.HouseRepository

class HouseViewModel (private val repository: HouseRepository = HouseRepository()): ViewModel() {

    private var _houseInfoMutableLiveData = repository.houseInfoMutableLiveData
    val houseInfoMutableLiveData: LiveData<House>
        get() = _houseInfoMutableLiveData


    fun dataHouse(){
        repository.houseData()
    }

    fun changeName(name: String){
        repository.changeName(name)
    }
}