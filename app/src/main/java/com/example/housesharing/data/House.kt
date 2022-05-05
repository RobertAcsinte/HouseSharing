package com.example.housesharing.data

import android.os.Parcel
import android.os.Parcelable

data class House(
    var name: String? = null,
    var userId: Boolean? = null
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<House> {
        override fun createFromParcel(parcel: Parcel): House {
            return House(parcel)
        }

        override fun newArray(size: Int): Array<House?> {
            return arrayOfNulls(size)
        }
    }

}