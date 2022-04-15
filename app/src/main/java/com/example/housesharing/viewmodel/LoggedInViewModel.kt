package com.example.housesharing.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.housesharing.model.AppRepository
import com.google.firebase.auth.FirebaseUser

class LoggedInViewModel(application: Application) : AndroidViewModel(application) {

    private var appRepository: AppRepository = AppRepository(application)

    private var _userMutableLiveData = appRepository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    private var _loggedOutMutableLiveData = appRepository.loggedOutMutableLiveData
    val loggedOutMutableLiveData: LiveData<Boolean>
        get() = _loggedOutMutableLiveData

    fun logOut(){
        appRepository.logOut()
    }

}