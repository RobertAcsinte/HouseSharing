package com.example.housesharing.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.model.AppRepository
import com.google.firebase.auth.FirebaseUser

class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {

    private var appRepository: AppRepository = AppRepository(application)

    private var _userMutableLiveData = appRepository.userMutableLiveData
    val userMutableLiveData: LiveData<FirebaseUser>
        get() = _userMutableLiveData

    fun register(email: String, password: String){
        appRepository.register(email, password)
    }

    fun login(email: String, password: String){
        appRepository.login(email, password)
    }
}