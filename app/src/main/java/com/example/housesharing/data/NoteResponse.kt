package com.example.housesharing.data

import com.example.housesharing.data.Note

data class NoteResponse(
    var notes: List<Note>? = null,
    var exception: Exception? = null
)
