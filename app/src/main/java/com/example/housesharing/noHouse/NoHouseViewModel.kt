package com.example.housesharing.noHouse

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Account


import com.example.housesharing.data.source.HouseRepository

class NoHouseViewModel(account: Account, private val repository: HouseRepository = HouseRepository()): ViewModel() {

    private val _nameLive = MutableLiveData<String>()
    val nameLive: LiveData<String>
        get() = _nameLive

    init {
        _nameLive.value = account.firstName!!
    }

    fun joinHouse(id: String): LiveData<Boolean> {
        return repository.joinHouse(id)
    }
}