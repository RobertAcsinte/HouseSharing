package com.example.housesharing.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Account
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.source.AccountsRepository
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    private var _accountInfoMutableLiveData = repository.accountInfoMutableLiveData
    val accountInfoMutableLiveData: LiveData<Account>
        get() = _accountInfoMutableLiveData

    private var _loggedOutMutableLiveData = repository.loggedOutMutableLiveData
    val loggedOutMutableLiveData: LiveData<Boolean>
        get() = _loggedOutMutableLiveData


    fun logOut(){
        repository.logOut()
    }


    fun updateFirstName(firstName: String): LiveData<Boolean>{
        return repository.updateFirstName(firstName)
    }

    fun updateLastName(lastName: String): LiveData<Boolean>{
        return repository.updateLastName(lastName)
    }

    fun fetch(){
        repository.fetchAccount()
    }

    fun updateEmail(email: String): LiveData<Exception>{
        return repository.updateEmail(email)
    }

    fun updatePassword(password: String): LiveData<Exception>{
        return repository.updatePassword(password)
    }
}