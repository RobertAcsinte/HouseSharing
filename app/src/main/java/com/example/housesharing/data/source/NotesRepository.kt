package com.example.housesharing.data.source

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.Note
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.utils.Constants.NOTES_REF
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NotesRepository {
    private val rootRef: FirebaseDatabase = Firebase.database
    private val notesRef: DatabaseReference = rootRef.getReference(NOTES_REF)

    fun getResponseFromRealtimeDatabaseUsingLiveData() : MutableLiveData<NoteResponse> {
        val mutableLiveData = MutableLiveData<NoteResponse>()
        notesRef.get().addOnCompleteListener { task ->
            val response = NoteResponse()
            if (task.isSuccessful) {
                val result = task.result
                result?.let {
                    response.notes = result.children.map { snapShot ->
                        snapShot.getValue(Note::class.java)!!
                    }
                }
            } else {
                response.exception = task.exception
            }
            mutableLiveData.value = response
        }
        return mutableLiveData
    }

    fun plm(){
        // Read from the database
        notesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val map = dataSnapshot.value as Map<String, Any>?
                Log.d(TAG, "Value is: $map")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}