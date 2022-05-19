package com.example.housesharing.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.AppointmentResponse
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.data.source.KitchenRepository
import com.example.housesharing.data.source.NotesRepository
import java.text.SimpleDateFormat
import java.util.*

class TodayViewModel (private val repositoryNotes: NotesRepository = NotesRepository(), private val repositoryKitchen: KitchenRepository = KitchenRepository(), private val cal: Calendar = Calendar.getInstance(
    Locale.ENGLISH)): ViewModel() {


    fun fetchNotes() : LiveData<NoteResponse> {
        return repositoryNotes.fetchNotes()
    }

    fun fetchKitchen(): LiveData<AppointmentResponse>{
        var day = SimpleDateFormat("dMyy", Locale.ENGLISH).format(cal.time).toString()
        var hour = SimpleDateFormat("k", Locale.ENGLISH).format(cal.time).toString().toInt()
        return repositoryKitchen.fetchReservationsToday(day, hour)
    }

}