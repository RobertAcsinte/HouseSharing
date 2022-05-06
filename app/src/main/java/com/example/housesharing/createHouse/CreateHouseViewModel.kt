package com.example.housesharing.createHouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.HouseResponse
import com.example.housesharing.data.source.HouseRepository

class CreateHouseViewModel(private val repository: HouseRepository = HouseRepository()): ViewModel() {

    fun createHouse(name: String): LiveData<HouseResponse> {
        return repository.createHouse(name)
    }
}