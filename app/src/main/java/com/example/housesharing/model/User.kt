package com.example.housesharing.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val uid: String, val name: String, val email: String): Parcelable {

}