package com.example.housesharing.addNotes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.createHouse.CreateHouseViewModel
import com.example.housesharing.databinding.FragmentAddNoteBinding
import com.example.housesharing.databinding.FragmentTodayBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_note,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = "Add Note"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        binding.floatingActionButtonSaveNote.setOnClickListener {
            var title: String = binding.editTextTextNoteTitle.text.toString()
            var content: String = binding.editTextTextNoteContent.text.toString()
            if(title.isNotEmpty() && content.isNotEmpty()){
                viewModel.addNote(title, content).observe(viewLifecycleOwner){
                    if(it.exception != null){
                        Toast.makeText(context, it.exception!!.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                    else{
                        view?.findNavController()?.navigate(R.id.action_addNoteFragment_to_notesFragment)
                    }
                }
            }
            else{
                Toast.makeText(context, "Please fill out all the fields!",
                    Toast.LENGTH_SHORT).show()
            }

        }

        return binding.root
    }


}