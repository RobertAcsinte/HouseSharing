package com.example.housesharing.today

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.housesharing.R
import com.example.housesharing.data.NoteResponse
import com.example.housesharing.databinding.FragmentTodayBinding
import com.example.housesharing.notes.NotesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class TodayFragment : Fragment() {

    private lateinit var todayViewModel: TodayViewModel
    private lateinit var notesViewModel: NotesViewModel

    private lateinit var binding: FragmentTodayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_today,
            container,
            false
        )

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarLoggedIn)
        (activity as AppCompatActivity).title = "Schedule of the day"

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        todayViewModel = ViewModelProvider(this).get(TodayViewModel::class.java)
        todayViewModel.userMutableLiveData.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                binding.textViewEmail.text = user.email
            }
        })

        todayViewModel.getData().observe(viewLifecycleOwner){
            binding.textViewHouseId.text = it.account!!.houseId
        }

        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.getResponseUsingLiveData().observe(viewLifecycleOwner) {
            print(it)
            Log.d("sloboz", it.toString())
            if(it.exception == null){
                Log.d("sloboz", "PULAAA")
            }
        }



        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item,requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }


    private fun print(noteResponse: NoteResponse) {
        noteResponse.notes?.let { products ->
            products.forEach{ product ->
                product.title?.let {
                    Log.d("PISAT", it)
                }
            }
        }

        noteResponse.exception?.let { exception ->
            exception.message?.let {
                Log.d("PISAT", it)
            }
        }
    }

    private fun getResponseUsingLiveData() {
        notesViewModel.getResponseUsingLiveData().observe(viewLifecycleOwner) {
            print(it)
            Log.d("sloboz", it.toString())
        }
    }

    fun basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        // [END read_message]
    }
}