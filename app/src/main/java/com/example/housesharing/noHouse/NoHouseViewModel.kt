package com.example.housesharing.noHouse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.model.Account

class NoHouseViewModel(account: Account): ViewModel() {

    private val _nameLive = MutableLiveData<String>()
    val nameLive: LiveData<String>
        get() = _nameLive

    init {
        _nameLive.value = account.firstName!!
    }
}