package com.example.housesharing.data

import android.os.Parcel
import android.os.Parcelable

data class House(
    var name: String? = null,
    var id: String? = null,
    var members: ArrayList<String> = ArrayList<String>())
