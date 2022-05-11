package com.example.housesharing.addNotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.data.source.NotesRepository

class AddNoteViewModel (
    private val repository: NotesRepository = NotesRepository()
): ViewModel() {

    fun addNote(title: String, content: String) : LiveData<NoteResponse> {
        return repository.createNote(title, content)
    }
}