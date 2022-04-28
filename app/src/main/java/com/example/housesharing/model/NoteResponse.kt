package com.example.housesharing.model

data class NoteResponse(
    var notes: List<Note>? = null,
    var exception: Exception? = null
)
