package com.example.housesharing.data.source

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.housesharing.data.Account
import com.example.housesharing.data.AccountResponse
import com.example.housesharing.data.Note
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.utils.Constants
import com.example.housesharing.utils.Constants.NOTES_REF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonCancellable.children

class NotesRepository {
    private val rootRef: FirebaseDatabase = Firebase.database
    private val notesRef: DatabaseReference = rootRef.getReference(NOTES_REF)
    private val accountRef: DatabaseReference = rootRef.getReference(Constants.ACCOUNT_REF)
    private val houseRef: DatabaseReference = rootRef.getReference(Constants.HOUSE_REF)

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        notesRef.keepSynced(true)
        accountRef.keepSynced(true)
        houseRef.keepSynced(true)
    }

    fun fetchNotes() : MutableLiveData<NoteResponse> {
        val mutableLiveData = MutableLiveData<NoteResponse>()
        accountRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
            if(task.isSuccessful){
                task.result.value.toString()?.let {
                    notesRef.child(it).get().addOnCompleteListener { task ->
                        val response = NoteResponse()
                        if (task.isSuccessful) {
                            val result = task.result
                            result?.let {
                                for(d: DataSnapshot in it.children) {
                                    //Log.d("SLOBOZ", d.value.toString())
                                    //Log.d("SLOBOZ", d.key.toString())
                                    //Log.d("SLOBOZ", d.child("date").value.toString())
                                    var note = Note(d.key.toString(), d.child("title").value.toString(),
                                        d.child("content").value.toString(), d.child("date").value.toString())
                                    response.notes?.add(note)
                                }
                                //response.notes = result.children.map { snapShot ->
                                //snapShot.getValue(Note::class.java)!!
                                //}

                            }
                        } else {
                            response.exception = task.exception
                        }
                        mutableLiveData.value = response
                    }
                }
            }
        }
        return mutableLiveData
    }

//    fun getHouseNotes() : String {
//        val notesId = ArrayList<String>()
//        val mutableLiveData = MutableLiveData<NoteResponse>()
//        accountRef.child(firebaseAuth.uid.toString()).child("houseId").get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                task.result.value.toString()?.let {
//                    houseRef.child(it).child("notes").get().addOnCompleteListener { task ->
//                        if(task.isSuccessful){
//                            notesId.add(task.result.chi)
//                        }
//                    }
//                }
//            }
//            mutableLiveData.value = response
//        }
//        return mutableLiveData
//    }

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