package com.example.housesharing.data

import com.example.housesharing.data.Note

data class NoteResponse(
    var notes: ArrayList<Note>? = ArrayList<Note>(),
    var exception: Exception? = null
)
