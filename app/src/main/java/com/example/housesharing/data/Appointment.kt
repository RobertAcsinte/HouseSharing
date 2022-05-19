package com.example.housesharing.data

data class Appointment (
    var id: Int? = null,
    var timeStartHour: Int? = null,
    var timeStartMinute: Int? = null,
    var timeEndHour: Int? = null,
    var timeEndMinute: Int? = null,
    var userId: String? = null,
    var firstName: String? = null,
    var lastName: String? = null
)