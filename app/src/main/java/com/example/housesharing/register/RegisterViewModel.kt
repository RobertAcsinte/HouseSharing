package com.example.housesharing.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.source.AccountsRepository
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel (private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    private var _userMutableLiveData = repository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    fun register(email: String, firstName: String, lastName: String, password: String): LiveData<AccountResponse>{
        return repository.register(email, firstName, lastName, password)
    }

}