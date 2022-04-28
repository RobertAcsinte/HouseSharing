package com.example.housesharing.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.model.AccountResponse
import com.example.housesharing.repository.AccountsRepository
import com.example.housesharing.repository.NotesRepository
import com.google.firebase.auth.FirebaseUser

class AccountViewModel(private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    private var _userMutableLiveData = repository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _loggedOutMutableLiveData = repository.loggedOutMutableLiveData
    val loggedOutMutableLiveData: LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    fun register(email: String, firstName: String, lastName: String, password: String): LiveData<AccountResponse>{
        return repository.register(email, firstName, lastName, password)
    }

    fun login(email: String, password: String): LiveData<AccountResponse>{
        return repository.login(email, password)
    }

    fun logOut(){
        repository.logOut()
    }
}