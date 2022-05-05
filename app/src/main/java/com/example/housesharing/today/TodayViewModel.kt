package com.example.housesharing.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.source.AccountsRepository
import com.google.firebase.auth.FirebaseUser

class TodayViewModel (private val repository: AccountsRepository = AccountsRepository()) :
    ViewModel() {

    private var _userMutableLiveData = repository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

}