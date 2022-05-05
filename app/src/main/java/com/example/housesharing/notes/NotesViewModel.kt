package com.example.housesharing.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.data.source.NotesRepository

class NotesViewModel (
    private val repository: NotesRepository = NotesRepository()
    ): ViewModel() {

    fun getResponseUsingLiveData() : LiveData<NoteResponse> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData()
    }
}