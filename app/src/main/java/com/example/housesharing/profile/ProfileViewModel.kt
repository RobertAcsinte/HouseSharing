package com.example.housesharing.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.source.AccountsRepository
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    private var _userMutableLiveData = repository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _loggedOutMutableLiveData = repository.loggedOutMutableLiveData
    val loggedOutMutableLiveData: LiveData<Boolean>
        get() = _loggedOutMutableLiveData


    fun logOut(){
        repository.logOut()
    }

    fun getData(): LiveData<AccountResponse>{
        return repository.accountData()
    }

}