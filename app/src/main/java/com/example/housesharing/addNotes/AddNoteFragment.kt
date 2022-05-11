package com.example.housesharing.addNotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentAddNoteBinding
import com.example.housesharing.databinding.FragmentTodayBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = "Add Note"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        return binding.root
    }

}