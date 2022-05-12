package com.example.housesharing.detailsNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.housesharing.data.Note
import com.example.housesharing.noHouse.NoHouseViewModel

class DetailsNoteViewModelFactory(private val note: Note): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsNoteViewModel::class.java)) {
            return DetailsNoteViewModel(note) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}