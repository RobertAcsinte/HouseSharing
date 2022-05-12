package com.example.housesharing.detailsNote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.housesharing.data.Note
import com.example.housesharing.data.source.HouseRepository
import com.example.housesharing.data.source.NotesRepository

class DetailsNoteViewModel(note: Note, private val repository: NotesRepository = NotesRepository()): ViewModel() {

    private val _noteId = MutableLiveData<String>()
    val noteId: LiveData<String>
        get() = _noteId

    private val _noteTitle = MutableLiveData<String>()
    val noteTitle: LiveData<String>
        get() = _noteTitle

    private val _noteContent = MutableLiveData<String>()
    val noteContent: LiveData<String>
        get() = _noteContent

    init {
        _noteId.value = note.id!!
        _noteTitle.value = note.title!!
        _noteContent.value = note.content!!
    }

    fun deleteNote(id: String): MutableLiveData<Boolean>{
        return repository.deleteNote(id)
    }
}