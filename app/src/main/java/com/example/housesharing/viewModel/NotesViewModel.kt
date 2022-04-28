package com.example.housesharing.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.model.NoteResponse
import com.example.housesharing.repository.NotesRepository

class NotesViewModel (
    private val repository: NotesRepository = NotesRepository()
    ): ViewModel() {

    fun getResponseUsingLiveData() : LiveData<NoteResponse> {
        return repository.getResponseFromRealtimeDatabaseUsingLiveData()
    }
}