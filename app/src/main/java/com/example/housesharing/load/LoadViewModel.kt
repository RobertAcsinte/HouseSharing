package com.example.housesharing.load

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.source.AccountsRepository

class LoadViewModel(private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    fun accountData(): MutableLiveData<AccountResponse> {
        return repository.accountData()
    }

}