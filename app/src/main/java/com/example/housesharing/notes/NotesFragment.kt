package com.example.housesharing.notes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.housesharing.R
import com.example.housesharing.data.Note
import com.example.housesharing.databinding.FragmentNotesBinding
import com.example.housesharing.load.LoadFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView


class NotesFragment : Fragment(), NotesAdapter.OnItemClickListener {

    private lateinit var notesViewModel: NotesViewModel

    private lateinit var binding: FragmentNotesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notes,
            container,
            false
        )

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarNotes)
        (activity as AppCompatActivity).title = "Notes"

        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE

        setHasOptionsMenu(true)



        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        notesViewModel.getResponseUsingLiveData().observe(viewLifecycleOwner) {
            if(it.exception == null){
                val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
                val adapter = NotesAdapter(it.notes!!, this)
                binding.recyclerViewNotes.layoutManager = manager
                binding.recyclerViewNotes.adapter = adapter
            }
        }

        binding.floatingActionButtonAddNote.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_notesFragment_to_addNoteFragment)
        }


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

    override fun onItemClick(item: Note?) {
//        Toast.makeText(context, item!!.id.toString(), Toast.LENGTH_SHORT).show()
        val action = NotesFragmentDirections.actionNotesFragmentToDetailsNoteFragment(item!!)
        view?.findNavController()?.navigate(action)
    }


}