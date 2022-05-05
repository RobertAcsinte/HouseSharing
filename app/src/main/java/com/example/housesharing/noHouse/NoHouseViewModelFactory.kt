package com.example.housesharing.noHouse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.housesharing.data.Account

class NoHouseViewModelFactory(private val account: Account): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoHouseViewModel::class.java)) {
            return NoHouseViewModel(account) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}