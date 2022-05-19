package com.example.housesharing.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.data.source.AccountsRepository
import com.example.housesharing.data.source.NotesRepository
import com.google.firebase.auth.FirebaseUser

class TodayViewModel ( private val repository: NotesRepository = NotesRepository() ): ViewModel() {

    fun fetchNotes() : LiveData<NoteResponse> {
        return repository.fetchNotes()
    }

}