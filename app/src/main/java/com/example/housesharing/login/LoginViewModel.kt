package com.example.housesharing.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.source.AccountsRepository
import com.google.firebase.auth.FirebaseUser

class LoginViewModel (private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    private var _userMutableLiveData = repository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    fun login(email: String, password: String): LiveData<AccountResponse> {
        return repository.login(email, password)
    }



}