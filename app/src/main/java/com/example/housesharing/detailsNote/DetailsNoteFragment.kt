package com.example.housesharing.detailsNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.housesharing.R
import com.example.housesharing.databinding.FragmentDetailsNoteBinding
import com.example.housesharing.noHouse.NoHouseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*


class DetailsNoteFragment : Fragment() {

    private lateinit var binding: FragmentDetailsNoteBinding

    private lateinit var viewModel: DetailsNoteViewModel
    private lateinit var viewModelFactory: DetailsNoteViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details_note,
            container,
            false)

        viewModelFactory = DetailsNoteViewModelFactory(DetailsNoteFragmentArgs.fromBundle(requireArguments()).note)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsNoteViewModel::class.java)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.editTextTextNoteDetailsTitle.setText(viewModel.noteTitle.value)
        binding.editTextTextNoteDetailsContent.setText(viewModel.noteContent.value)

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        setHasOptionsMenu(true)


        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }
        inflater.inflate(R.menu.notes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteNote) {
            MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
                .setTitle("Confirm deleting?")
                .setPositiveButton("Yes"){dialog, which ->
                    viewModel.deleteNote(viewModel.noteId.value.toString()).observe(viewLifecycleOwner){
                        view?.findNavController()?.popBackStack()
                    }
                }
                .setNegativeButton("No"){dialog, which ->
                }
                .show()
        }
        if (item.itemId == R.id.editNote) {
            viewModel.editNote(viewModel.noteId.value.toString(),
                    binding.editTextTextNoteDetailsTitle.text.toString(), binding.editTextTextNoteDetailsContent.text.toString()).observe(viewLifecycleOwner){
                view?.findNavController()?.popBackStack()
            }
        }

        return super.onOptionsItemSelected(item);
    }


}